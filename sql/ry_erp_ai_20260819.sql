-- ============================================================
-- ERP 智能助手 增量脚本（2026-08-19）
-- 前置：先导入 ry_20260417.sql、ry_config_20260611.sql、ry_erp_20260818.sql
-- 说明：新增「智能助手」顶级菜单及其子菜单（AI 助手对话页），
--       前端页面组件位于 ruoyi-ui/src/views/erp/ai/index.vue，
--       对话引擎为前端 Mock（话术/意图模拟），回复中的经营数字取自真实报表接口。
-- ============================================================

-- 1. 智能助手顶级目录
insert into sys_menu values('2080', '智能助手', '0',  '12', 'erp/ai', null, '', '', 1, 0, 'M', '0', '0', '', 'message', 'admin', sysdate(), '', null, 'ERP智能助手目录');

-- 2. AI 助手对话页
insert into sys_menu values('2081', 'AI 助手', '2080', '1', 'index', 'erp/ai/index', '', '', 1, 0, 'C', '0', '0', 'erp:ai:chat', 'message', 'admin', sysdate(), '', null, 'AI智能对话助手菜单');

-- 3. 角色授权（普通角色 role_id=2；admin 用户 isAdmin 自动可见全部菜单）
insert into sys_role_menu values ('2', '2080');
insert into sys_role_menu values ('2', '2081');
