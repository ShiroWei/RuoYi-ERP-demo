-- ============================================================
-- ERP 报表中心 增量脚本（2026-08-19）
-- 前置：先导入 ry_20260417.sql、ry_config_20260611.sql、ry_erp_20260818.sql
-- 说明：新增「报表中心」顶级菜单及其四个子菜单（采购/销售/库存/利润报表），
--       前端页面组件位于 ruoyi-ui/src/views/erp/report/ 下，数据由前端 mock 提供。
-- ============================================================

-- 1. 报表中心顶级目录
insert into sys_menu values('2060', '报表中心', '0',  '11', 'erp/report', null, '', '', 1, 0, 'M', '0', '0', '',                  'chart',    'admin', sysdate(), '', null, 'ERP报表中心目录');

-- 2. 采购报表
insert into sys_menu values('2061', '采购报表', '2060', '1', 'purchase', 'erp/report/purchase/index', '', '', 1, 0, 'C', '0', '0', 'erp:report:purchase:list', 'shopping', 'admin', sysdate(), '', null, '采购分析报表菜单');

-- 3. 销售报表
insert into sys_menu values('2062', '销售报表', '2060', '2', 'sale', 'erp/report/sale/index', '', '', 1, 0, 'C', '0', '0', 'erp:report:sale:list',     'money',    'admin', sysdate(), '', null, '销售分析报表菜单');

-- 4. 库存报表
insert into sys_menu values('2063', '库存报表', '2060', '3', 'stock', 'erp/report/stock/index', '', '', 1, 0, 'C', '0', '0', 'erp:report:stock:list',    'list',     'admin', sysdate(), '', null, '库存分析报表菜单');

-- 5. 利润报表
insert into sys_menu values('2064', '利润报表', '2060', '4', 'profit', 'erp/report/profit/index', '', '', 1, 0, 'C', '0', '0', 'erp:report:profit:list',   'pie-chart', 'admin', sysdate(), '', null, '利润分析报表菜单');

-- 6. 角色授权（普通角色 role_id=2；admin 用户 isAdmin 自动可见全部菜单）
insert into sys_role_menu values ('2', '2060');
insert into sys_role_menu values ('2', '2061');
insert into sys_role_menu values ('2', '2062');
insert into sys_role_menu values ('2', '2063');
insert into sys_role_menu values ('2', '2064');