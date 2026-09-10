param(
    [switch]$SkipFrontend,
    [int]$FrontendPort = 3000,
    [string]$NacosHome = $env:NACOS_HOME
)

$ErrorActionPreference = 'Stop'
$ProjectRoot = [System.IO.Path]::GetFullPath((Join-Path $PSScriptRoot '..'))
$RuntimeDir = Join-Path $env:TEMP 'erp-demo-startup'
if ([string]::IsNullOrWhiteSpace($NacosHome)) { $NacosHome = 'D:\env\nacos-server-3.0.2' }

$services = @(
    [pscustomobject]@{ Name='auth'; Port=9200; Jar='ruoyi-auth\target\ruoyi-auth.jar'; Work='ruoyi-auth' },
    [pscustomobject]@{ Name='system'; Port=9201; Jar='ruoyi-modules\ruoyi-system\target\ruoyi-modules-system.jar'; Work='ruoyi-modules\ruoyi-system' },
    [pscustomobject]@{ Name='erp-base'; Port=9217; Jar='ruoyi-modules\ruoyi-erp-base\target\ruoyi-modules-erp-base.jar'; Work='ruoyi-modules\ruoyi-erp-base' },
    [pscustomobject]@{ Name='erp-purchase'; Port=9218; Jar='ruoyi-modules\ruoyi-erp-purchase\target\ruoyi-modules-erp-purchase.jar'; Work='ruoyi-modules\ruoyi-erp-purchase' },
    [pscustomobject]@{ Name='erp-sale'; Port=9219; Jar='ruoyi-modules\ruoyi-erp-sale\target\ruoyi-modules-erp-sale.jar'; Work='ruoyi-modules\ruoyi-erp-sale' },
    [pscustomobject]@{ Name='erp-stock'; Port=9220; Jar='ruoyi-modules\ruoyi-erp-stock\target\ruoyi-modules-erp-stock.jar'; Work='ruoyi-modules\ruoyi-erp-stock' },
    [pscustomobject]@{ Name='erp-finance'; Port=9221; Jar='ruoyi-modules\ruoyi-erp-finance\target\ruoyi-modules-erp-finance.jar'; Work='ruoyi-modules\ruoyi-erp-finance' },
    [pscustomobject]@{ Name='erp-production'; Port=9222; Jar='ruoyi-modules\ruoyi-erp-production\target\ruoyi-modules-erp-production.jar'; Work='ruoyi-modules\ruoyi-erp-production' },
    [pscustomobject]@{ Name='erp-report'; Port=9223; Jar='ruoyi-modules\ruoyi-erp-report\target\ruoyi-modules-erp-report.jar'; Work='ruoyi-modules\ruoyi-erp-report' },
    [pscustomobject]@{ Name='gateway'; Port=8000; Jar='ruoyi-gateway\target\ruoyi-gateway.jar'; Work='ruoyi-gateway' }
)

function Info([string]$Message) { Write-Host "[INFO] $Message" }
function Ok([string]$Message) { Write-Host "[OK] $Message" -ForegroundColor Green }
function Warn([string]$Message) { Write-Host "[WARN] $Message" -ForegroundColor Yellow }
function Fail([string]$Message) { throw $Message }

function PortOpen([int]$Port) {
    return $null -ne (Get-NetTCPConnection -LocalPort $Port -State Listen -ErrorAction SilentlyContinue | Select-Object -First 1)
}

function PortOwner([int]$Port) {
    $connection = Get-NetTCPConnection -LocalPort $Port -State Listen -ErrorAction SilentlyContinue | Select-Object -First 1
    if ($null -eq $connection) { return $null }
    return Get-CimInstance Win32_Process -Filter "ProcessId=$($connection.OwningProcess)" -ErrorAction SilentlyContinue
}

function Stop-ErpProcesses {
    Info 'Cleaning ERP-owned processes only; OA/shared services are preserved.'
    $rootPattern = [regex]::Escape($ProjectRoot)
    $processes = Get-CimInstance Win32_Process -ErrorAction SilentlyContinue | Where-Object {
        $_.CommandLine -and (
            $_.CommandLine -match 'ruoyi-modules-erp-(base|purchase|sale|stock|finance|production|report)\.jar' -or
            ($_.CommandLine -match 'vue-cli-service' -and $_.CommandLine -match $rootPattern)
        )
    }
    foreach ($process in $processes) {
        Stop-Process -Id $process.ProcessId -Force -ErrorAction SilentlyContinue
    }
    Start-Sleep -Seconds 2
    foreach ($port in 9217, 9218, 9219, 9220, 9221, 9222, 9223, $FrontendPort) {
        if (PortOpen $port) { Fail "ERP port $port is still occupied after cleanup." }
    }
    Ok 'ERP-owned processes cleaned.'
}

function Start-Nacos {
    if (PortOpen 8848) { Ok 'Nacos is already listening on 8848.'; return }
    $startup = Join-Path $NacosHome 'bin\startup.cmd'
    if (-not (Test-Path -LiteralPath $startup)) { Fail "Nacos startup script not found: $startup" }

    $stale = Get-CimInstance Win32_Process -Filter "Name='java.exe'" -ErrorAction SilentlyContinue | Where-Object { $_.CommandLine -match 'nacos-server' }
    foreach ($process in $stale) { Stop-Process -Id $process.ProcessId -Force -ErrorAction SilentlyContinue }
    Start-Sleep -Seconds 2

    $log = Join-Path $RuntimeDir 'nacos-start.log'
    New-Item -ItemType Directory -Force -Path $RuntimeDir | Out-Null
    Start-Process -FilePath 'cmd.exe' -ArgumentList '/c', "startup.cmd -m standalone > `"$log`" 2>&1" -WorkingDirectory (Split-Path $startup) -WindowStyle Hidden | Out-Null
    for ($i = 0; $i -lt 120; $i++) {
        if (PortOpen 8848) { Ok 'Nacos is listening on 8848.'; return }
        Start-Sleep -Seconds 1
    }
    Fail "Nacos did not become ready. Check $log"
}

function Start-Jar([pscustomobject]$Service) {
    $jar = Join-Path $ProjectRoot $Service.Jar
    if (-not (Test-Path -LiteralPath $jar)) { Fail "Jar not found: $jar. Build it with Maven first." }
    if (PortOpen $Service.Port) {
        $owner = PortOwner $Service.Port
        Warn "$($Service.Name) port $($Service.Port) is already occupied by PID $($owner.ProcessId); reusing it."
        return
    }
    $log = Join-Path $RuntimeDir "$($Service.Name).out.log"
    $work = Join-Path $ProjectRoot $Service.Work
    $command = "java -Dfile.encoding=utf-8 -Xms256m -Xmx512m -jar `"$jar`" > `"$log`" 2>&1"
    Start-Process -FilePath 'cmd.exe' -ArgumentList '/c', $command -WorkingDirectory $work -WindowStyle Hidden | Out-Null
    Info "Starting $($Service.Name) on $($Service.Port)..."
}

function Wait-Service([pscustomobject]$Service) {
    for ($i = 0; $i -lt 120; $i++) {
        if (PortOpen $Service.Port) { Ok "$($Service.Name) is listening on $($Service.Port)."; return }
        Start-Sleep -Seconds 1
    }
    Fail "$($Service.Name) did not become ready on $($Service.Port). Check $RuntimeDir."
}

try {
    New-Item -ItemType Directory -Force -Path $RuntimeDir | Out-Null
    Write-Host '========================================'
    Write-Host '  RuoYi ERP Demo - Start All'
    Write-Host '========================================'
    Write-Host "Root : $ProjectRoot"
    Write-Host "Nacos: $NacosHome"

    if ($null -eq (Get-Command java -ErrorAction SilentlyContinue)) { Fail 'Java was not found in PATH.' }
    if (-not (PortOpen 3306)) { Fail 'MySQL is not listening on 3306.' }
    if (-not (PortOpen 6379)) { Fail 'Redis is not listening on 6379.' }
    Stop-ErpProcesses
    Start-Nacos

    foreach ($service in $services | Where-Object { $_.Name -ne 'gateway' }) { Start-Jar $service }
    foreach ($service in $services | Where-Object { $_.Name -ne 'gateway' }) { Wait-Service $service }

    # The gateway is shared with OA. Reuse it when running; start it only when free.
    $gateway = $services | Where-Object { $_.Name -eq 'gateway' }
    if (PortOpen 8000) {
        Warn 'Gateway port 8000 is already occupied; preserving the shared gateway/OA process.'
    } else {
        Start-Jar $gateway
        Wait-Service $gateway
    }

    if (-not $SkipFrontend) {
        $ui = Join-Path $ProjectRoot 'ruoyi-ui'
        if (-not (Test-Path -LiteralPath (Join-Path $ui 'node_modules'))) {
            Fail 'ruoyi-ui\node_modules is missing. Run npm install first.'
        }
        if (PortOpen $FrontendPort) {
            Warn "ERP frontend port $FrontendPort is already occupied; preserving it."
        } else {
            Start-Process -FilePath 'cmd.exe' -ArgumentList '/c', "set port=$FrontendPort&& npm run dev" -WorkingDirectory $ui -WindowStyle Hidden | Out-Null
            for ($i = 0; $i -lt 120; $i++) { if (PortOpen $FrontendPort) { Ok "ERP frontend is listening on $FrontendPort."; break }; Start-Sleep -Seconds 1 }
            if (-not (PortOpen $FrontendPort)) { Fail "ERP frontend did not become ready on $FrontendPort." }
        }
    }

    Write-Host ''
    Write-Host '[OK] ERP startup finished. OA/shared services were preserved.' -ForegroundColor Green
    Write-Host 'Gateway : http://localhost:8000'
    Write-Host 'Frontend: http://localhost:3000'
    Write-Host 'Nacos   : http://localhost:18088'
    Write-Host 'Login   : admin / admin123'
    exit 0
}
catch {
    Write-Host "[FAILED] $($_.Exception.Message)" -ForegroundColor Red
    Write-Host "Logs: $RuntimeDir" -ForegroundColor Yellow
    exit 1
}
