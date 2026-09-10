-- ============================================================
-- ERP 普通角色功能权限（2026-09-10）
-- 角色：role_id=2（普通角色）
-- 策略：允许日常查询、新增、修改和审核；不授予删除权限。
-- 可重复执行。
-- ============================================================

-- 创建 ERP 功能按钮权限。按钮挂在对应业务菜单下，不显示为菜单项。
insert into sys_menu
(menu_id, menu_name, parent_id, order_num, path, component, query, route_name,
 is_frame, is_cache, menu_type, visible, status, perms, icon,
 create_by, create_time, update_by, update_time, remark)
select 3001, '物料新增', 2071, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'erp:material:add', '#', 'admin', sysdate(), '', null, ''
where not exists (select 1 from sys_menu where menu_id = 3001 or perms = 'erp:material:add');
insert into sys_menu select 3002, '物料修改', 2071, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'erp:material:edit', '#', 'admin', sysdate(), '', null, ''
where not exists (select 1 from sys_menu where menu_id = 3002 or perms = 'erp:material:edit');
insert into sys_menu select 3003, '供应商新增', 2072, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'erp:supplier:add', '#', 'admin', sysdate(), '', null, ''
where not exists (select 1 from sys_menu where menu_id = 3003 or perms = 'erp:supplier:add');
insert into sys_menu select 3004, '供应商修改', 2072, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'erp:supplier:edit', '#', 'admin', sysdate(), '', null, ''
where not exists (select 1 from sys_menu where menu_id = 3004 or perms = 'erp:supplier:edit');
insert into sys_menu select 3005, '客户新增', 2073, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'erp:customer:add', '#', 'admin', sysdate(), '', null, ''
where not exists (select 1 from sys_menu where menu_id = 3005 or perms = 'erp:customer:add');
insert into sys_menu select 3006, '客户修改', 2073, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'erp:customer:edit', '#', 'admin', sysdate(), '', null, ''
where not exists (select 1 from sys_menu where menu_id = 3006 or perms = 'erp:customer:edit');
insert into sys_menu select 3007, '仓库新增', 2074, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'erp:warehouse:add', '#', 'admin', sysdate(), '', null, ''
where not exists (select 1 from sys_menu where menu_id = 3007 or perms = 'erp:warehouse:add');
insert into sys_menu select 3008, '仓库修改', 2074, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'erp:warehouse:edit', '#', 'admin', sysdate(), '', null, ''
where not exists (select 1 from sys_menu where menu_id = 3008 or perms = 'erp:warehouse:edit');

insert into sys_menu select 3010, '采购订单查询', 2011, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'erp:purchaseOrder:query', '#', 'admin', sysdate(), '', null, ''
where not exists (select 1 from sys_menu where menu_id = 3010 or perms = 'erp:purchaseOrder:query');
insert into sys_menu select 3011, '采购订单新增', 2011, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'erp:purchaseOrder:add', '#', 'admin', sysdate(), '', null, ''
where not exists (select 1 from sys_menu where menu_id = 3011 or perms = 'erp:purchaseOrder:add');
insert into sys_menu select 3012, '采购订单修改审核', 2011, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'erp:purchaseOrder:edit', '#', 'admin', sysdate(), '', null, ''
where not exists (select 1 from sys_menu where menu_id = 3012 or perms = 'erp:purchaseOrder:edit');
insert into sys_menu select 3013, '采购入库查询', 2012, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'erp:purchaseInbound:query', '#', 'admin', sysdate(), '', null, ''
where not exists (select 1 from sys_menu where menu_id = 3013 or perms = 'erp:purchaseInbound:query');
insert into sys_menu select 3014, '采购入库新增', 2012, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'erp:purchaseInbound:add', '#', 'admin', sysdate(), '', null, ''
where not exists (select 1 from sys_menu where menu_id = 3014 or perms = 'erp:purchaseInbound:add');
insert into sys_menu select 3015, '采购入库修改审核', 2012, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'erp:purchaseInbound:edit', '#', 'admin', sysdate(), '', null, ''
where not exists (select 1 from sys_menu where menu_id = 3015 or perms = 'erp:purchaseInbound:edit');
insert into sys_menu select 3016, '采购退货查询', 2013, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'erp:purchaseReturn:query', '#', 'admin', sysdate(), '', null, ''
where not exists (select 1 from sys_menu where menu_id = 3016 or perms = 'erp:purchaseReturn:query');
insert into sys_menu select 3017, '采购退货新增', 2013, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'erp:purchaseReturn:add', '#', 'admin', sysdate(), '', null, ''
where not exists (select 1 from sys_menu where menu_id = 3017 or perms = 'erp:purchaseReturn:add');
insert into sys_menu select 3018, '采购退货修改审核', 2013, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'erp:purchaseReturn:edit', '#', 'admin', sysdate(), '', null, ''
where not exists (select 1 from sys_menu where menu_id = 3018 or perms = 'erp:purchaseReturn:edit');

insert into sys_menu select 3020, '销售订单查询', 2021, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'erp:saleOrder:query', '#', 'admin', sysdate(), '', null, ''
where not exists (select 1 from sys_menu where menu_id = 3020 or perms = 'erp:saleOrder:query');
insert into sys_menu select 3021, '销售订单新增', 2021, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'erp:saleOrder:add', '#', 'admin', sysdate(), '', null, ''
where not exists (select 1 from sys_menu where menu_id = 3021 or perms = 'erp:saleOrder:add');
insert into sys_menu select 3022, '销售订单修改审核', 2021, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'erp:saleOrder:edit', '#', 'admin', sysdate(), '', null, ''
where not exists (select 1 from sys_menu where menu_id = 3022 or perms = 'erp:saleOrder:edit');
insert into sys_menu select 3023, '销售出库查询', 2022, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'erp:saleOutbound:query', '#', 'admin', sysdate(), '', null, ''
where not exists (select 1 from sys_menu where menu_id = 3023 or perms = 'erp:saleOutbound:query');
insert into sys_menu select 3024, '销售出库新增', 2022, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'erp:saleOutbound:add', '#', 'admin', sysdate(), '', null, ''
where not exists (select 1 from sys_menu where menu_id = 3024 or perms = 'erp:saleOutbound:add');
insert into sys_menu select 3025, '销售出库修改审核', 2022, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'erp:saleOutbound:edit', '#', 'admin', sysdate(), '', null, ''
where not exists (select 1 from sys_menu where menu_id = 3025 or perms = 'erp:saleOutbound:edit');
insert into sys_menu select 3026, '销售退货查询', 2023, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'erp:saleReturn:query', '#', 'admin', sysdate(), '', null, ''
where not exists (select 1 from sys_menu where menu_id = 3026 or perms = 'erp:saleReturn:query');
insert into sys_menu select 3027, '销售退货新增', 2023, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'erp:saleReturn:add', '#', 'admin', sysdate(), '', null, ''
where not exists (select 1 from sys_menu where menu_id = 3027 or perms = 'erp:saleReturn:add');
insert into sys_menu select 3028, '销售退货修改审核', 2023, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'erp:saleReturn:edit', '#', 'admin', sysdate(), '', null, ''
where not exists (select 1 from sys_menu where menu_id = 3028 or perms = 'erp:saleReturn:edit');

insert into sys_menu select 3030, '出入库记录新增', 2032, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'erp:stockRecord:add', '#', 'admin', sysdate(), '', null, ''
where not exists (select 1 from sys_menu where menu_id = 3030 or perms = 'erp:stockRecord:add');
insert into sys_menu select 3031, '库存盘点新增', 2033, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'erp:stockCheck:add', '#', 'admin', sysdate(), '', null, ''
where not exists (select 1 from sys_menu where menu_id = 3031 or perms = 'erp:stockCheck:add');
insert into sys_menu select 3032, '库存盘点修改', 2033, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'erp:stockCheck:edit', '#', 'admin', sysdate(), '', null, ''
where not exists (select 1 from sys_menu where menu_id = 3032 or perms = 'erp:stockCheck:edit');
insert into sys_menu select 3033, '库存调拨新增', 2034, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'erp:stockTransfer:add', '#', 'admin', sysdate(), '', null, ''
where not exists (select 1 from sys_menu where menu_id = 3033 or perms = 'erp:stockTransfer:add');
insert into sys_menu select 3034, '库存调拨修改审核', 2034, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'erp:stockTransfer:edit', '#', 'admin', sysdate(), '', null, ''
where not exists (select 1 from sys_menu where menu_id = 3034 or perms = 'erp:stockTransfer:edit');

insert into sys_menu select 3040, '收付款新增', 2043, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'erp:payment:add', '#', 'admin', sysdate(), '', null, ''
where not exists (select 1 from sys_menu where menu_id = 3040 or perms = 'erp:payment:add');
insert into sys_menu select 3041, '收付款修改审核', 2043, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'erp:payment:edit', '#', 'admin', sysdate(), '', null, ''
where not exists (select 1 from sys_menu where menu_id = 3041 or perms = 'erp:payment:edit');

insert into sys_menu select 3050, 'BOM查询', 2051, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'erp:bom:query', '#', 'admin', sysdate(), '', null, ''
where not exists (select 1 from sys_menu where menu_id = 3050 or perms = 'erp:bom:query');
insert into sys_menu select 3051, 'BOM新增', 2051, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'erp:bom:add', '#', 'admin', sysdate(), '', null, ''
where not exists (select 1 from sys_menu where menu_id = 3051 or perms = 'erp:bom:add');
insert into sys_menu select 3052, 'BOM修改', 2051, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'erp:bom:edit', '#', 'admin', sysdate(), '', null, ''
where not exists (select 1 from sys_menu where menu_id = 3052 or perms = 'erp:bom:edit');
insert into sys_menu select 3053, '生产工单新增', 2052, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'erp:workOrder:add', '#', 'admin', sysdate(), '', null, ''
where not exists (select 1 from sys_menu where menu_id = 3053 or perms = 'erp:workOrder:add');
insert into sys_menu select 3054, '生产工单修改', 2052, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'erp:workOrder:edit', '#', 'admin', sysdate(), '', null, ''
where not exists (select 1 from sys_menu where menu_id = 3054 or perms = 'erp:workOrder:edit');

-- 将以上 ERP 功能权限授权给普通角色；不包含任何 remove 权限。
insert ignore into sys_role_menu(role_id, menu_id)
select 2, menu_id from sys_menu
where menu_id between 3001 and 3054
  and perms is not null
  and perms not like '%:remove';
