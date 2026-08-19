# AGENTS.md

## 提交规范（Commit Convention）

- **一个功能一次 commit**：每个独立功能/修复必须单独提交，禁止把多个功能打包成一个大 commit。
- 每个 commit 只包含该功能的文件；相关联但独立的改动（如顺带发现的 bug 修复）单独 commit。
- 提交前先 `git status` / `git diff` 核对只暂存本次功能相关文件。
- 工作完成、提交前必须验证（后端 `mvn compile/package` 通过、前端 vue 文件编译/静态检查通过、关键接口可用）。

### Commit message 风格

参考仓库既有风格，使用中文，可用类型前缀或业务域前缀：

- `feat(域)：功能描述`（如 `feat(智能助手)：新增AI对话助手`）
- `fix(前端)：修复...`、`fix(后端)：...`
- `docs：更新README...`
- `sql：新增...脚本`

## 项目要点

- 前端：`ruoyi-ui`（Vue 2 + Element UI），dev server 端口 3000，代理 `/dev-api` → 网关 8000。
- 后端 ERP：`ruoyi-modules/ruoyi-erp`（端口 9202），报表/列表接口为 AjaxResult/TableDataInfo 包装，前端需解包 `res.data` / `res.rows`。
- 菜单：动态路由来自 `sys_menu`，新增页面需同步 SQL 脚本并导入 `ry-cloud` 库。
- AI 助手为 Mock 引擎（话术模拟），经营数字取自真实报表接口，不接入真实 AI 服务。
