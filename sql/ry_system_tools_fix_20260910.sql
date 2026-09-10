-- ============================================================
-- 系统监控 / 系统工具菜单修复（2026-09-10）
-- 说明：共享 sys_menu 环境下修正可用控制台地址，隐藏仓库未提供的 Sentinel 控制台。
-- 可重复执行。
-- ============================================================

-- Nacos 3.x 控制台实际运行在 18088。
update sys_menu
set path = 'http://localhost:18088',
    update_by = 'admin',
    update_time = sysdate(),
    remark = 'Nacos 3.x 服务治理控制台'
where menu_id = 112;

-- SpringDoc 聚合入口位于网关 8000。
update sys_menu
set path = 'http://localhost:8000/swagger-ui.html',
    update_by = 'admin',
    update_time = sysdate(),
    remark = '网关聚合接口文档'
where menu_id = 116;

-- 仓库没有 Sentinel Dashboard 可执行服务，隐藏该入口，避免必然打不开。
update sys_menu
set visible = '1',
    status = '1',
    update_by = 'admin',
    update_time = sysdate(),
    remark = '当前项目未提供 Sentinel Dashboard，菜单已隐藏'
where menu_id = 111;

-- 以下菜单保留并确保正常显示：在线用户、定时任务、Nacos、Admin监控、表单构建、代码生成、系统接口。
update sys_menu
set visible = '0', status = '0', update_by = 'admin', update_time = sysdate()
where menu_id in (109, 110, 112, 113, 114, 115, 116);
