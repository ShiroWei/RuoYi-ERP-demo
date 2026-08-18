-- ----------------------------
-- ERP 企业管理平台 二次开发脚本
-- 内容：ERP 业务表 DDL + ERP 菜单 + ERP 字典 + 角色授权
-- 说明：mock 优先阶段，业务表先建好供真实接口后续接入；菜单/字典当前即生效
-- 执行：mysql -h 127.0.0.1 -P 3306 -uroot --default-character-set=utf8mb4 ry-cloud < sql\ry_erp_20260818.sql
-- ----------------------------

-- ============================================================
-- 一、ERP 业务表 DDL（供后续真实接口接入，当前 mock 阶段可先建表）
-- ============================================================

-- ----------------------------
-- 1. 物料档案
-- ----------------------------
drop table if exists erp_material;
create table erp_material
(
  material_id      bigint(20)      not null auto_increment    comment '物料ID',
  material_code    varchar(50)     default ''                 comment '物料编码',
  material_name    varchar(100)    not null                   comment '物料名称',
  category         varchar(50)     default ''                 comment '物料分类',
  specification    varchar(100)    default ''                 comment '规格型号',
  unit             varchar(20)     default ''                 comment '计量单位',
  purchase_price   decimal(12,2)   default 0                  comment '采购单价',
  sale_price       decimal(12,2)   default 0                  comment '销售单价',
  safe_stock       decimal(12,2)   default 0                  comment '安全库存',
  status           char(1)         default '0'                comment '状态（0正常 1停用）',
  remark           varchar(500)    default null               comment '备注',
  create_by        varchar(64)     default ''                 comment '创建者',
  create_time      datetime                                   comment '创建时间',
  update_by        varchar(64)     default ''                 comment '更新者',
  update_time      datetime                                   comment '更新时间',
  del_flag         char(1)         default '0'                comment '删除标志（0代表存在 2代表删除）',
  primary key (material_id)
) engine=innodb auto_increment=1 comment = '物料档案表';

-- ----------------------------
-- 2. 供应商档案
-- ----------------------------
drop table if exists erp_supplier;
create table erp_supplier
(
  supplier_id      bigint(20)      not null auto_increment    comment '供应商ID',
  supplier_code    varchar(50)     default ''                 comment '供应商编码',
  supplier_name    varchar(100)    not null                   comment '供应商名称',
  supplier_type    varchar(20)     default ''                 comment '供应商类型',
  contact_person   varchar(50)     default ''                 comment '联系人',
  phone            varchar(20)     default ''                 comment '联系电话',
  email            varchar(50)     default ''                 comment '邮箱',
  address          varchar(200)    default ''                 comment '地址',
  tax_no           varchar(50)     default ''                 comment '税号',
  bank_name        varchar(100)    default ''                 comment '开户银行',
  bank_account     varchar(50)     default ''                 comment '银行账号',
  status           char(1)         default '0'                comment '状态（0正常 1停用）',
  remark           varchar(500)    default null               comment '备注',
  create_by        varchar(64)     default ''                 comment '创建者',
  create_time      datetime                                   comment '创建时间',
  update_by        varchar(64)     default ''                 comment '更新者',
  update_time      datetime                                   comment '更新时间',
  del_flag         char(1)         default '0'                comment '删除标志（0代表存在 2代表删除）',
  primary key (supplier_id)
) engine=innodb auto_increment=1 comment = '供应商档案表';

-- ----------------------------
-- 3. 客户档案
-- ----------------------------
drop table if exists erp_customer;
create table erp_customer
(
  customer_id      bigint(20)      not null auto_increment    comment '客户ID',
  customer_code    varchar(50)     default ''                 comment '客户编码',
  customer_name    varchar(100)    not null                   comment '客户名称',
  customer_type    varchar(20)     default ''                 comment '客户类型',
  customer_level   varchar(20)     default ''                 comment '客户等级',
  contact_person   varchar(50)     default ''                 comment '联系人',
  phone            varchar(20)     default ''                 comment '联系电话',
  email            varchar(50)     default ''                 comment '邮箱',
  address          varchar(200)    default ''                 comment '地址',
  credit_limit     decimal(12,2)   default 0                  comment '信用额度',
  status           char(1)         default '0'                comment '状态（0正常 1停用）',
  remark           varchar(500)    default null               comment '备注',
  create_by        varchar(64)     default ''                 comment '创建者',
  create_time      datetime                                   comment '创建时间',
  update_by        varchar(64)     default ''                 comment '更新者',
  update_time      datetime                                   comment '更新时间',
  del_flag         char(1)         default '0'                comment '删除标志（0代表存在 2代表删除）',
  primary key (customer_id)
) engine=innodb auto_increment=1 comment = '客户档案表';

-- ----------------------------
-- 4. 仓库档案
-- ----------------------------
drop table if exists erp_warehouse;
create table erp_warehouse
(
  warehouse_id     bigint(20)      not null auto_increment    comment '仓库ID',
  warehouse_code   varchar(50)     default ''                 comment '仓库编码',
  warehouse_name   varchar(100)    not null                   comment '仓库名称',
  warehouse_type   varchar(20)     default ''                 comment '仓库类型',
  manager          varchar(50)     default ''                 comment '负责人',
  phone            varchar(20)     default ''                 comment '联系电话',
  address          varchar(200)    default ''                 comment '地址',
  status           char(1)         default '0'                comment '状态（0正常 1停用）',
  remark           varchar(500)    default null               comment '备注',
  create_by        varchar(64)     default ''                 comment '创建者',
  create_time      datetime                                   comment '创建时间',
  update_by        varchar(64)     default ''                 comment '更新者',
  update_time      datetime                                   comment '更新时间',
  del_flag         char(1)         default '0'                comment '删除标志（0代表存在 2代表删除）',
  primary key (warehouse_id)
) engine=innodb auto_increment=1 comment = '仓库档案表';

-- ----------------------------
-- 5. 采购订单主表
-- ----------------------------
drop table if exists erp_purchase_order;
create table erp_purchase_order
(
  order_id         bigint(20)      not null auto_increment    comment '订单ID',
  order_no         varchar(50)     default ''                 comment '订单编号',
  supplier_id      bigint(20)      default 0                  comment '供应商ID',
  order_date       datetime                                   comment '下单日期',
  total_amount     decimal(12,2)   default 0                  comment '订单总额',
  status           char(1)         default '0'                comment '单据状态（0草稿 1待审核 2审核通过 3已驳回 4已完成）',
  remark           varchar(500)    default null               comment '备注',
  create_by        varchar(64)     default ''                 comment '创建者',
  create_time      datetime                                   comment '创建时间',
  update_by        varchar(64)     default ''                 comment '更新者',
  update_time      datetime                                   comment '更新时间',
  primary key (order_id)
) engine=innodb auto_increment=1 comment = '采购订单主表';

-- ----------------------------
-- 6. 采购订单明细表
-- ----------------------------
drop table if exists erp_purchase_order_item;
create table erp_purchase_order_item
(
  item_id          bigint(20)      not null auto_increment    comment '明细ID',
  order_id         bigint(20)      not null                   comment '订单ID',
  material_id      bigint(20)      default 0                  comment '物料ID',
  material_code    varchar(50)     default ''                 comment '物料编码',
  material_name    varchar(100)    default ''                 comment '物料名称',
  specification    varchar(100)    default ''                 comment '规格型号',
  unit             varchar(20)     default ''                 comment '计量单位',
  quantity         decimal(12,2)   default 0                  comment '数量',
  price            decimal(12,2)   default 0                  comment '单价',
  amount           decimal(12,2)   default 0                  comment '金额',
  remark           varchar(500)    default null               comment '备注',
  primary key (item_id)
) engine=innodb auto_increment=1 comment = '采购订单明细表';

-- ----------------------------
-- 7. 采购入库单
-- ----------------------------
drop table if exists erp_purchase_inbound;
create table erp_purchase_inbound
(
  inbound_id       bigint(20)      not null auto_increment    comment '入库单ID',
  inbound_no       varchar(50)     default ''                 comment '入库单号',
  order_id         bigint(20)      default 0                  comment '关联采购订单ID',
  supplier_id      bigint(20)      default 0                  comment '供应商ID',
  warehouse_id     bigint(20)      default 0                  comment '仓库ID',
  inbound_date     datetime                                   comment '入库日期',
  total_amount     decimal(12,2)   default 0                  comment '入库金额',
  status           char(1)         default '0'                comment '单据状态（0草稿 1待审核 2审核通过 3已驳回 4已完成）',
  remark           varchar(500)    default null               comment '备注',
  create_by        varchar(64)     default ''                 comment '创建者',
  create_time      datetime                                   comment '创建时间',
  update_by        varchar(64)     default ''                 comment '更新者',
  update_time      datetime                                   comment '更新时间',
  primary key (inbound_id)
) engine=innodb auto_increment=1 comment = '采购入库单表';

-- ----------------------------
-- 8. 采购退货单
-- ----------------------------
drop table if exists erp_purchase_return;
create table erp_purchase_return
(
  return_id        bigint(20)      not null auto_increment    comment '退货单ID',
  return_no        varchar(50)     default ''                 comment '退货单号',
  order_id         bigint(20)      default 0                  comment '关联采购订单ID',
  supplier_id      bigint(20)      default 0                  comment '供应商ID',
  warehouse_id     bigint(20)      default 0                  comment '仓库ID',
  return_date      datetime                                   comment '退货日期',
  total_amount     decimal(12,2)   default 0                  comment '退货金额',
  status           char(1)         default '0'                comment '单据状态（0草稿 1待审核 2审核通过 3已驳回 4已完成）',
  reason           varchar(500)    default ''                 comment '退货原因',
  remark           varchar(500)    default null               comment '备注',
  create_by        varchar(64)     default ''                 comment '创建者',
  create_time      datetime                                   comment '创建时间',
  update_by        varchar(64)     default ''                 comment '更新者',
  update_time      datetime                                   comment '更新时间',
  primary key (return_id)
) engine=innodb auto_increment=1 comment = '采购退货单表';

-- ----------------------------
-- 9. 销售订单主表
-- ----------------------------
drop table if exists erp_sale_order;
create table erp_sale_order
(
  order_id         bigint(20)      not null auto_increment    comment '订单ID',
  order_no         varchar(50)     default ''                 comment '订单编号',
  customer_id      bigint(20)      default 0                  comment '客户ID',
  order_date       datetime                                   comment '下单日期',
  total_amount     decimal(12,2)   default 0                  comment '订单总额',
  status           char(1)         default '0'                comment '单据状态（0草稿 1待审核 2审核通过 3已驳回 4已完成）',
  remark           varchar(500)    default null               comment '备注',
  create_by        varchar(64)     default ''                 comment '创建者',
  create_time      datetime                                   comment '创建时间',
  update_by        varchar(64)     default ''                 comment '更新者',
  update_time      datetime                                   comment '更新时间',
  primary key (order_id)
) engine=innodb auto_increment=1 comment = '销售订单主表';

-- ----------------------------
-- 10. 销售订单明细表
-- ----------------------------
drop table if exists erp_sale_order_item;
create table erp_sale_order_item
(
  item_id          bigint(20)      not null auto_increment    comment '明细ID',
  order_id         bigint(20)      not null                   comment '订单ID',
  material_id      bigint(20)      default 0                  comment '物料ID',
  material_code    varchar(50)     default ''                 comment '物料编码',
  material_name    varchar(100)    default ''                 comment '物料名称',
  specification    varchar(100)    default ''                 comment '规格型号',
  unit             varchar(20)     default ''                 comment '计量单位',
  quantity         decimal(12,2)   default 0                  comment '数量',
  price            decimal(12,2)   default 0                  comment '单价',
  amount           decimal(12,2)   default 0                  comment '金额',
  remark           varchar(500)    default null               comment '备注',
  primary key (item_id)
) engine=innodb auto_increment=1 comment = '销售订单明细表';

-- ----------------------------
-- 11. 销售出库单
-- ----------------------------
drop table if exists erp_sale_outbound;
create table erp_sale_outbound
(
  outbound_id      bigint(20)      not null auto_increment    comment '出库单ID',
  outbound_no      varchar(50)     default ''                 comment '出库单号',
  order_id         bigint(20)      default 0                  comment '关联销售订单ID',
  customer_id      bigint(20)      default 0                  comment '客户ID',
  warehouse_id     bigint(20)      default 0                  comment '仓库ID',
  outbound_date    datetime                                   comment '出库日期',
  total_amount     decimal(12,2)   default 0                  comment '出库金额',
  status           char(1)         default '0'                comment '单据状态（0草稿 1待审核 2审核通过 3已驳回 4已完成）',
  remark           varchar(500)    default null               comment '备注',
  create_by        varchar(64)     default ''                 comment '创建者',
  create_time      datetime                                   comment '创建时间',
  update_by        varchar(64)     default ''                 comment '更新者',
  update_time      datetime                                   comment '更新时间',
  primary key (outbound_id)
) engine=innodb auto_increment=1 comment = '销售出库单表';

-- ----------------------------
-- 12. 销售退货单
-- ----------------------------
drop table if exists erp_sale_return;
create table erp_sale_return
(
  return_id        bigint(20)      not null auto_increment    comment '退货单ID',
  return_no        varchar(50)     default ''                 comment '退货单号',
  order_id         bigint(20)      default 0                  comment '关联销售订单ID',
  customer_id      bigint(20)      default 0                  comment '客户ID',
  warehouse_id     bigint(20)      default 0                  comment '仓库ID',
  return_date      datetime                                   comment '退货日期',
  total_amount     decimal(12,2)   default 0                  comment '退货金额',
  status           char(1)         default '0'                comment '单据状态（0草稿 1待审核 2审核通过 3已驳回 4已完成）',
  reason           varchar(500)    default ''                 comment '退货原因',
  remark           varchar(500)    default null               comment '备注',
  create_by        varchar(64)     default ''                 comment '创建者',
  create_time      datetime                                   comment '创建时间',
  update_by        varchar(64)     default ''                 comment '更新者',
  update_time      datetime                                   comment '更新时间',
  primary key (return_id)
) engine=innodb auto_increment=1 comment = '销售退货单表';

-- ----------------------------
-- 13. 库存表
-- ----------------------------
drop table if exists erp_stock;
create table erp_stock
(
  stock_id         bigint(20)      not null auto_increment    comment '库存ID',
  warehouse_id     bigint(20)      default 0                  comment '仓库ID',
  material_id      bigint(20)      default 0                  comment '物料ID',
  quantity         decimal(12,2)   default 0                  comment '库存数量',
  create_by        varchar(64)     default ''                 comment '创建者',
  create_time      datetime                                   comment '创建时间',
  update_by        varchar(64)     default ''                 comment '更新者',
  update_time      datetime                                   comment '更新时间',
  unique key uk_warehouse_material (warehouse_id, material_id),
  primary key (stock_id)
) engine=innodb auto_increment=1 comment = '库存表';

-- ----------------------------
-- 14. 出入库记录表
-- ----------------------------
drop table if exists erp_stock_record;
create table erp_stock_record
(
  record_id        bigint(20)      not null auto_increment    comment '记录ID',
  record_no        varchar(50)     default ''                 comment '单号',
  record_type      varchar(20)     default ''                 comment '出入库类型',
  direction        char(1)         default '1'                comment '方向（0出库 1入库）',
  warehouse_id     bigint(20)      default 0                  comment '仓库ID',
  material_id      bigint(20)      default 0                  comment '物料ID',
  quantity         decimal(12,2)   default 0                  comment '数量',
  biz_no           varchar(50)     default ''                 comment '关联业务单号',
  operator         varchar(50)     default ''                 comment '经办人',
  record_date      datetime                                   comment '发生日期',
  remark           varchar(500)    default null               comment '备注',
  create_by        varchar(64)     default ''                 comment '创建者',
  create_time      datetime                                   comment '创建时间',
  update_by        varchar(64)     default ''                 comment '更新者',
  update_time      datetime                                   comment '更新时间',
  primary key (record_id)
) engine=innodb auto_increment=1 comment = '出入库记录表';

-- ----------------------------
-- 15. 库存盘点表
-- ----------------------------
drop table if exists erp_stock_check;
create table erp_stock_check
(
  check_id         bigint(20)      not null auto_increment    comment '盘点ID',
  check_no         varchar(50)     default ''                 comment '盘点单号',
  warehouse_id     bigint(20)      default 0                  comment '仓库ID',
  material_id      bigint(20)      default 0                  comment '物料ID',
  book_qty         decimal(12,2)   default 0                  comment '账面数量',
  actual_qty       decimal(12,2)   default 0                  comment '实盘数量',
  diff_qty         decimal(12,2)   default 0                  comment '差异数量',
  check_date       datetime                                   comment '盘点日期',
  status           char(1)         default '0'                comment '盘点状态（0未盘点 1已盘点）',
  remark           varchar(500)    default null               comment '备注',
  create_by        varchar(64)     default ''                 comment '创建者',
  create_time      datetime                                   comment '创建时间',
  update_by        varchar(64)     default ''                 comment '更新者',
  update_time      datetime                                   comment '更新时间',
  primary key (check_id)
) engine=innodb auto_increment=1 comment = '库存盘点表';

-- ----------------------------
-- 16. 库存调拨表
-- ----------------------------
drop table if exists erp_stock_transfer;
create table erp_stock_transfer
(
  transfer_id      bigint(20)      not null auto_increment    comment '调拨ID',
  transfer_no      varchar(50)     default ''                 comment '调拨单号',
  from_warehouse_id bigint(20)     default 0                  comment '调出仓库ID',
  to_warehouse_id  bigint(20)      default 0                  comment '调入仓库ID',
  material_id      bigint(20)      default 0                  comment '物料ID',
  quantity         decimal(12,2)   default 0                  comment '调拨数量',
  transfer_date    datetime                                   comment '调拨日期',
  status           char(1)         default '0'                comment '单据状态（0草稿 1待审核 2审核通过 3已驳回 4已完成）',
  remark           varchar(500)    default null               comment '备注',
  create_by        varchar(64)     default ''                 comment '创建者',
  create_time      datetime                                   comment '创建时间',
  update_by        varchar(64)     default ''                 comment '更新者',
  update_time      datetime                                   comment '更新时间',
  primary key (transfer_id)
) engine=innodb auto_increment=1 comment = '库存调拨表';

-- ----------------------------
-- 17. 应收账款表
-- ----------------------------
drop table if exists erp_receivable;
create table erp_receivable
(
  receivable_id    bigint(20)      not null auto_increment    comment '应收ID',
  bill_no          varchar(50)     default ''                 comment '业务单号',
  bill_type        varchar(20)     default ''                 comment '业务类型',
  customer_id      bigint(20)      default 0                  comment '客户ID',
  amount           decimal(12,2)   default 0                  comment '应收金额',
  received_amount  decimal(12,2)   default 0                  comment '已收金额',
  balance          decimal(12,2)   default 0                  comment '未收金额',
  due_date         datetime                                   comment '到期日',
  status           char(1)         default '0'                comment '状态（0未结清 1部分结清 2已结清）',
  remark           varchar(500)    default null               comment '备注',
  create_by        varchar(64)     default ''                 comment '创建者',
  create_time      datetime                                   comment '创建时间',
  update_by        varchar(64)     default ''                 comment '更新者',
  update_time      datetime                                   comment '更新时间',
  primary key (receivable_id)
) engine=innodb auto_increment=1 comment = '应收账款表';

-- ----------------------------
-- 18. 应付账款表
-- ----------------------------
drop table if exists erp_payable;
create table erp_payable
(
  payable_id       bigint(20)      not null auto_increment    comment '应付ID',
  bill_no          varchar(50)     default ''                 comment '业务单号',
  bill_type        varchar(20)     default ''                 comment '业务类型',
  supplier_id      bigint(20)      default 0                  comment '供应商ID',
  amount           decimal(12,2)   default 0                  comment '应付金额',
  paid_amount      decimal(12,2)   default 0                  comment '已付金额',
  balance          decimal(12,2)   default 0                  comment '未付金额',
  due_date         datetime                                   comment '到期日',
  status           char(1)         default '0'                comment '状态（0未结清 1部分结清 2已结清）',
  remark           varchar(500)    default null               comment '备注',
  create_by        varchar(64)     default ''                 comment '创建者',
  create_time      datetime                                   comment '创建时间',
  update_by        varchar(64)     default ''                 comment '更新者',
  update_time      datetime                                   comment '更新时间',
  primary key (payable_id)
) engine=innodb auto_increment=1 comment = '应付账款表';

-- ----------------------------
-- 19. 收付款单表
-- ----------------------------
drop table if exists erp_payment;
create table erp_payment
(
  payment_id       bigint(20)      not null auto_increment    comment '单ID',
  payment_no       varchar(50)     default ''                 comment '单号',
  payment_type     char(1)         default '1'                comment '类型（1收款 2付款）',
  bill_no          varchar(50)     default ''                 comment '关联业务单号',
  partner_type     varchar(20)     default ''                 comment '往来单位类型（客户/供应商）',
  partner_id       bigint(20)      default 0                  comment '往来单位ID',
  amount           decimal(12,2)   default 0                  comment '金额',
  payment_date     datetime                                   comment '收付款日期',
  status           char(1)         default '0'                comment '单据状态（0草稿 1待审核 2审核通过 3已驳回 4已完成）',
  remark           varchar(500)    default null               comment '备注',
  create_by        varchar(64)     default ''                 comment '创建者',
  create_time      datetime                                   comment '创建时间',
  update_by        varchar(64)     default ''                 comment '更新者',
  update_time      datetime                                   comment '更新时间',
  primary key (payment_id)
) engine=innodb auto_increment=1 comment = '收付款单表';

-- ----------------------------
-- 20. 物料清单(BOM)主表
-- ----------------------------
drop table if exists erp_bom;
create table erp_bom
(
  bom_id           bigint(20)      not null auto_increment    comment 'BOM ID',
  bom_no           varchar(50)     default ''                 comment 'BOM编号',
  product_id       bigint(20)      default 0                  comment '成品物料ID',
  product_code     varchar(50)     default ''                 comment '成品编码',
  product_name     varchar(100)    default ''                 comment '成品名称',
  unit             varchar(20)     default ''                 comment '计量单位',
  status           char(1)         default '0'                comment '状态（0正常 1停用）',
  remark           varchar(500)    default null               comment '备注',
  create_by        varchar(64)     default ''                 comment '创建者',
  create_time      datetime                                   comment '创建时间',
  update_by        varchar(64)     default ''                 comment '更新者',
  update_time      datetime                                   comment '更新时间',
  primary key (bom_id)
) engine=innodb auto_increment=1 comment = '物料清单(BOM)主表';

-- ----------------------------
-- 21. 物料清单(BOM)明细表
-- ----------------------------
drop table if exists erp_bom_item;
create table erp_bom_item
(
  item_id          bigint(20)      not null auto_increment    comment '明细ID',
  bom_id           bigint(20)      not null                   comment 'BOM ID',
  material_id      bigint(20)      default 0                  comment '物料ID',
  material_code    varchar(50)     default ''                 comment '物料编码',
  material_name    varchar(100)    default ''                 comment '物料名称',
  specification    varchar(100)    default ''                 comment '规格型号',
  unit             varchar(20)     default ''                 comment '计量单位',
  quantity         decimal(12,2)   default 0                  comment '用量',
  remark           varchar(500)    default null               comment '备注',
  primary key (item_id)
) engine=innodb auto_increment=1 comment = '物料清单(BOM)明细表';

-- ----------------------------
-- 22. 生产工单表
-- ----------------------------
drop table if exists erp_production_order;
create table erp_production_order
(
  order_id         bigint(20)      not null auto_increment    comment '工单ID',
  order_no         varchar(50)     default ''                 comment '工单编号',
  product_id       bigint(20)      default 0                  comment '成品物料ID',
  product_code     varchar(50)     default ''                 comment '成品编码',
  product_name     varchar(100)    default ''                 comment '成品名称',
  plan_qty         decimal(12,2)   default 0                  comment '计划数量',
  finish_qty       decimal(12,2)   default 0                  comment '完工数量',
  order_date       datetime                                   comment '下达日期',
  plan_start_date  datetime                                   comment '计划开工日期',
  plan_end_date    datetime                                   comment '计划完工日期',
  status           char(1)         default '0'                comment '状态（0未开始 1生产中 2已完工 3已关闭）',
  remark           varchar(500)    default null               comment '备注',
  create_by        varchar(64)     default ''                 comment '创建者',
  create_time      datetime                                   comment '创建时间',
  update_by        varchar(64)     default ''                 comment '更新者',
  update_time      datetime                                   comment '更新时间',
  primary key (order_id)
) engine=innodb auto_increment=1 comment = '生产工单表';

-- ============================================================
-- 二、ERP 菜单（admin 登录自动可见；普通角色授权见第三节）
-- ============================================================

-- 一级菜单
insert into sys_menu values('2000', '基础资料', '0',   '5', 'erp/base',      null, '', '', 1, 0, 'M', '0', '0', '',                   'nested',   'admin', sysdate(), '', null, 'ERP基础资料目录');
insert into sys_menu values('2010', '采购管理', '0',   '6', 'erp/purchase',  null, '', '', 1, 0, 'M', '0', '0', '',                   'shopping', 'admin', sysdate(), '', null, 'ERP采购管理目录');
insert into sys_menu values('2020', '销售管理', '0',   '7', 'erp/sale',      null, '', '', 1, 0, 'M', '0', '0', '',                   'money',    'admin', sysdate(), '', null, 'ERP销售管理目录');
insert into sys_menu values('2030', '库存管理', '0',   '8', 'erp/stock',     null, '', '', 1, 0, 'M', '0', '0', '',                   'list',     'admin', sysdate(), '', null, 'ERP库存管理目录');
insert into sys_menu values('2040', '财务管理', '0',   '9', 'erp/finance',   null, '', '', 1, 0, 'M', '0', '0', '',                   'money',    'admin', sysdate(), '', null, 'ERP财务管理目录');
insert into sys_menu values('2050', '生产管理', '0',  '10', 'erp/production', null, '', '', 1, 0, 'M', '0', '0', '',                  'build',    'admin', sysdate(), '', null, 'ERP生产管理目录');

-- 二级菜单：基础资料
insert into sys_menu values('2001', '物料管理', '2000', '1', 'material',  'erp/base/material/index',  '', '', 1, 0, 'C', '0', '0', 'erp:material:list',     'form',     'admin', sysdate(), '', null, '物料档案管理菜单');
insert into sys_menu values('2002', '供应商管理', '2000', '2', 'supplier',  'erp/base/supplier/index',  '', '', 1, 0, 'C', '0', '0', 'erp:supplier:list',     'peoples',  'admin', sysdate(), '', null, '供应商档案管理菜单');
insert into sys_menu values('2003', '客户管理', '2000', '3', 'customer',  'erp/base/customer/index',  '', '', 1, 0, 'C', '0', '0', 'erp:customer:list',     'people',   'admin', sysdate(), '', null, '客户档案管理菜单');
insert into sys_menu values('2004', '仓库管理', '2000', '4', 'warehouse', 'erp/base/warehouse/index', '', '', 1, 0, 'C', '0', '0', 'erp:warehouse:list',    'table',    'admin', sysdate(), '', null, '仓库档案管理菜单');

-- 二级菜单：采购管理
insert into sys_menu values('2011', '采购订单', '2010', '1', 'order', 'erp/purchase/order/index', '', '', 1, 0, 'C', '0', '0', 'erp:purchase:order:list',    'form',     'admin', sysdate(), '', null, '采购订单管理菜单');
insert into sys_menu values('2012', '采购入库', '2010', '2', 'inbound', 'erp/purchase/inbound/index', '', '', 1, 0, 'C', '0', '0', 'erp:purchase:inbound:list',  'download', 'admin', sysdate(), '', null, '采购入库单管理菜单');
insert into sys_menu values('2013', '采购退货', '2010', '3', 'return', 'erp/purchase/return/index', '', '', 1, 0, 'C', '0', '0', 'erp:purchase:return:list',   'upload',   'admin', sysdate(), '', null, '采购退货单管理菜单');

-- 二级菜单：销售管理
insert into sys_menu values('2021', '销售订单', '2020', '1', 'order', 'erp/sale/order/index', '', '', 1, 0, 'C', '0', '0', 'erp:sale:order:list',    'form',     'admin', sysdate(), '', null, '销售订单管理菜单');
insert into sys_menu values('2022', '销售出库', '2020', '2', 'outbound', 'erp/sale/outbound/index', '', '', 1, 0, 'C', '0', '0', 'erp:sale:outbound:list',  'download', 'admin', sysdate(), '', null, '销售出库单管理菜单');
insert into sys_menu values('2023', '销售退货', '2020', '3', 'return', 'erp/sale/return/index', '', '', 1, 0, 'C', '0', '0', 'erp:sale:return:list',   'upload',   'admin', sysdate(), '', null, '销售退货单管理菜单');

-- 二级菜单：库存管理
insert into sys_menu values('2031', '库存查询', '2030', '1', 'inventory', 'erp/stock/inventory/index', '', '', 1, 0, 'C', '0', '0', 'erp:stock:inventory:list', 'search',   'admin', sysdate(), '', null, '库存查询菜单');
insert into sys_menu values('2032', '出入库单据', '2030', '2', 'record', 'erp/stock/record/index', '', '', 1, 0, 'C', '0', '0', 'erp:stock:record:list',     'log',      'admin', sysdate(), '', null, '出入库单据管理菜单');
insert into sys_menu values('2033', '库存盘点', '2030', '3', 'check', 'erp/stock/check/index', '', '', 1, 0, 'C', '0', '0', 'erp:stock:check:list',     'clipboard', 'admin', sysdate(), '', null, '库存盘点管理菜单');
insert into sys_menu values('2034', '库存调拨', '2030', '4', 'transfer', 'erp/stock/transfer/index', '', '', 1, 0, 'C', '0', '0', 'erp:stock:transfer:list',  'drag',     'admin', sysdate(), '', null, '库存调拨管理菜单');

-- 二级菜单：财务管理
insert into sys_menu values('2041', '应收账款', '2040', '1', 'receivable', 'erp/finance/receivable/index', '', '', 1, 0, 'C', '0', '0', 'erp:finance:receivable:list', 'money',  'admin', sysdate(), '', null, '应收账款管理菜单');
insert into sys_menu values('2042', '应付账款', '2040', '2', 'payable', 'erp/finance/payable/index', '', '', 1, 0, 'C', '0', '0', 'erp:finance:payable:list',     'money',  'admin', sysdate(), '', null, '应付账款管理菜单');
insert into sys_menu values('2043', '收付款单', '2040', '3', 'payment', 'erp/finance/payment/index', '', '', 1, 0, 'C', '0', '0', 'erp:finance:payment:list',    'edit',   'admin', sysdate(), '', null, '收付款单管理菜单');

-- 二级菜单：生产管理
insert into sys_menu values('2051', '物料清单', '2050', '1', 'bom', 'erp/production/bom/index', '', '', 1, 0, 'C', '0', '0', 'erp:production:bom:list',   'list',   'admin', sysdate(), '', null, '物料清单(BOM)管理菜单');
insert into sys_menu values('2052', '生产工单', '2050', '2', 'order', 'erp/production/order/index', '', '', 1, 0, 'C', '0', '0', 'erp:production:order:list', 'build',  'admin', sysdate(), '', null, '生产工单管理菜单');

-- ============================================================
-- 三、角色授权（普通角色 role_id=2；admin 用户 isAdmin 自动可见全部菜单）
-- ============================================================
insert into sys_role_menu values ('2', '2000');
insert into sys_role_menu values ('2', '2001');
insert into sys_role_menu values ('2', '2002');
insert into sys_role_menu values ('2', '2003');
insert into sys_role_menu values ('2', '2004');
insert into sys_role_menu values ('2', '2010');
insert into sys_role_menu values ('2', '2011');
insert into sys_role_menu values ('2', '2012');
insert into sys_role_menu values ('2', '2013');
insert into sys_role_menu values ('2', '2020');
insert into sys_role_menu values ('2', '2021');
insert into sys_role_menu values ('2', '2022');
insert into sys_role_menu values ('2', '2023');
insert into sys_role_menu values ('2', '2030');
insert into sys_role_menu values ('2', '2031');
insert into sys_role_menu values ('2', '2032');
insert into sys_role_menu values ('2', '2033');
insert into sys_role_menu values ('2', '2034');
insert into sys_role_menu values ('2', '2040');
insert into sys_role_menu values ('2', '2041');
insert into sys_role_menu values ('2', '2042');
insert into sys_role_menu values ('2', '2043');
insert into sys_role_menu values ('2', '2050');
insert into sys_role_menu values ('2', '2051');
insert into sys_role_menu values ('2', '2052');

-- ============================================================
-- 四、ERP 字典类型
-- ============================================================
insert into sys_dict_type values(100, '单据状态', 'erp_bill_status',        '0', 'admin', sysdate(), '', null, 'ERP单据状态列表');
insert into sys_dict_type values(101, '单据类型', 'erp_bill_type',          '0', 'admin', sysdate(), '', null, 'ERP单据类型列表');
insert into sys_dict_type values(102, '物料分类', 'erp_material_category',  '0', 'admin', sysdate(), '', null, '物料分类列表');
insert into sys_dict_type values(103, '计量单位', 'erp_unit',               '0', 'admin', sysdate(), '', null, '计量单位列表');
insert into sys_dict_type values(104, '供应商类型', 'erp_supplier_type',    '0', 'admin', sysdate(), '', null, '供应商类型列表');
insert into sys_dict_type values(105, '客户类型', 'erp_customer_type',      '0', 'admin', sysdate(), '', null, '客户类型列表');
insert into sys_dict_type values(106, '客户等级', 'erp_customer_level',     '0', 'admin', sysdate(), '', null, '客户等级列表');
insert into sys_dict_type values(107, '仓库类型', 'erp_warehouse_type',     '0', 'admin', sysdate(), '', null, '仓库类型列表');
insert into sys_dict_type values(108, '出入库类型', 'erp_stock_record_type', '0', 'admin', sysdate(), '', null, '出入库类型列表');
insert into sys_dict_type values(109, '盘点状态', 'erp_check_status',       '0', 'admin', sysdate(), '', null, '盘点状态列表');
insert into sys_dict_type values(110, '收付款类型', 'erp_payment_type',     '0', 'admin', sysdate(), '', null, '收付款类型列表');
insert into sys_dict_type values(111, '往来结清状态', 'erp_settle_status',  '0', 'admin', sysdate(), '', null, '往来账结清状态列表');
insert into sys_dict_type values(112, '生产状态', 'erp_production_status',  '0', 'admin', sysdate(), '', null, '生产状态列表');
insert into sys_dict_type values(113, '优先级', 'erp_priority',             '0', 'admin', sysdate(), '', null, '优先级列表');

-- ============================================================
-- 五、ERP 字典数据
-- ============================================================
insert into sys_dict_data values(200, 1,  '草稿',     '0', 'erp_bill_status',         '',   'info',    'N', '0', 'admin', sysdate(), '', null, '草稿状态');
insert into sys_dict_data values(201, 2,  '待审核',   '1', 'erp_bill_status',         '',   'warning', 'N', '0', 'admin', sysdate(), '', null, '待审核状态');
insert into sys_dict_data values(202, 3,  '审核通过', '2', 'erp_bill_status',         '',   'primary', 'N', '0', 'admin', sysdate(), '', null, '审核通过状态');
insert into sys_dict_data values(203, 4,  '已驳回',   '3', 'erp_bill_status',         '',   'danger',  'N', '0', 'admin', sysdate(), '', null, '已驳回状态');
insert into sys_dict_data values(204, 5,  '已完成',   '4', 'erp_bill_status',         '',   'success', 'N', '0', 'admin', sysdate(), '', null, '已完成状态');

insert into sys_dict_data values(205, 1,  '采购订单', '1', 'erp_bill_type',           '',   '',        'N', '0', 'admin', sysdate(), '', null, '采购订单');
insert into sys_dict_data values(206, 2,  '采购入库', '2', 'erp_bill_type',           '',   '',        'N', '0', 'admin', sysdate(), '', null, '采购入库');
insert into sys_dict_data values(207, 3,  '采购退货', '3', 'erp_bill_type',           '',   '',        'N', '0', 'admin', sysdate(), '', null, '采购退货');
insert into sys_dict_data values(208, 4,  '销售订单', '4', 'erp_bill_type',           '',   '',        'N', '0', 'admin', sysdate(), '', null, '销售订单');
insert into sys_dict_data values(209, 5,  '销售出库', '5', 'erp_bill_type',           '',   '',        'N', '0', 'admin', sysdate(), '', null, '销售出库');
insert into sys_dict_data values(210, 6,  '销售退货', '6', 'erp_bill_type',           '',   '',        'N', '0', 'admin', sysdate(), '', null, '销售退货');
insert into sys_dict_data values(211, 7,  '库存调拨', '7', 'erp_bill_type',           '',   '',        'N', '0', 'admin', sysdate(), '', null, '库存调拨');
insert into sys_dict_data values(212, 8,  '生产工单', '8', 'erp_bill_type',           '',   '',        'N', '0', 'admin', sysdate(), '', null, '生产工单');
insert into sys_dict_data values(213, 9,  '收付款单', '9', 'erp_bill_type',           '',   '',        'N', '0', 'admin', sysdate(), '', null, '收付款单');
insert into sys_dict_data values(214, 10, '库存盘点', '10', 'erp_bill_type',          '',   '',        'N', '0', 'admin', sysdate(), '', null, '库存盘点');

insert into sys_dict_data values(215, 1,  '原材料',   '1', 'erp_material_category',   '',   '',        'N', '0', 'admin', sysdate(), '', null, '原材料分类');
insert into sys_dict_data values(216, 2,  '半成品',   '2', 'erp_material_category',   '',   '',        'N', '0', 'admin', sysdate(), '', null, '半成品分类');
insert into sys_dict_data values(217, 3,  '成品',     '3', 'erp_material_category',   '',   '',        'N', '0', 'admin', sysdate(), '', null, '成品分类');
insert into sys_dict_data values(218, 4,  '包装材料', '4', 'erp_material_category',   '',   '',        'N', '0', 'admin', sysdate(), '', null, '包装材料分类');
insert into sys_dict_data values(219, 5,  '办公用品', '5', 'erp_material_category',   '',   '',        'N', '0', 'admin', sysdate(), '', null, '办公用品分类');

insert into sys_dict_data values(220, 1,  '件',   '1', 'erp_unit',   '', '', 'N', '0', 'admin', sysdate(), '', null, '计量单位件');
insert into sys_dict_data values(221, 2,  '箱',   '2', 'erp_unit',   '', '', 'N', '0', 'admin', sysdate(), '', null, '计量单位箱');
insert into sys_dict_data values(222, 3,  '公斤', '3', 'erp_unit',   '', '', 'N', '0', 'admin', sysdate(), '', null, '计量单位公斤');
insert into sys_dict_data values(223, 4,  '吨',   '4', 'erp_unit',   '', '', 'N', '0', 'admin', sysdate(), '', null, '计量单位吨');
insert into sys_dict_data values(224, 5,  '米',   '5', 'erp_unit',   '', '', 'N', '0', 'admin', sysdate(), '', null, '计量单位米');
insert into sys_dict_data values(225, 6,  '个',   '6', 'erp_unit',   '', '', 'N', '0', 'admin', sysdate(), '', null, '计量单位个');
insert into sys_dict_data values(226, 7,  '台',   '7', 'erp_unit',   '', '', 'N', '0', 'admin', sysdate(), '', null, '计量单位台');
insert into sys_dict_data values(227, 8,  '套',   '8', 'erp_unit',   '', '', 'N', '0', 'admin', sysdate(), '', null, '计量单位套');

insert into sys_dict_data values(228, 1,  '生产厂家', '1', 'erp_supplier_type', '', '', 'N', '0', 'admin', sysdate(), '', null, '生产厂家');
insert into sys_dict_data values(229, 2,  '经销商',   '2', 'erp_supplier_type', '', '', 'N', '0', 'admin', sysdate(), '', null, '经销商');
insert into sys_dict_data values(230, 3,  '代理商',   '3', 'erp_supplier_type', '', '', 'N', '0', 'admin', sysdate(), '', null, '代理商');
insert into sys_dict_data values(231, 4,  '服务商',   '4', 'erp_supplier_type', '', '', 'N', '0', 'admin', sysdate(), '', null, '服务商');

insert into sys_dict_data values(232, 1,  '企业客户', '1', 'erp_customer_type', '', '', 'N', '0', 'admin', sysdate(), '', null, '企业客户');
insert into sys_dict_data values(233, 2,  '个人客户', '2', 'erp_customer_type', '', '', 'N', '0', 'admin', sysdate(), '', null, '个人客户');
insert into sys_dict_data values(234, 3,  '经销商',   '3', 'erp_customer_type', '', '', 'N', '0', 'admin', sysdate(), '', null, '经销商');
insert into sys_dict_data values(235, 4,  '政府机构', '4', 'erp_customer_type', '', '', 'N', '0', 'admin', sysdate(), '', null, '政府机构');

insert into sys_dict_data values(236, 1,  '普通', '1', 'erp_customer_level', '', 'info',    'N', '0', 'admin', sysdate(), '', null, '普通等级');
insert into sys_dict_data values(237, 2,  '白银', '2', 'erp_customer_level', '', 'primary', 'N', '0', 'admin', sysdate(), '', null, '白银等级');
insert into sys_dict_data values(238, 3,  '黄金', '3', 'erp_customer_level', '', 'warning', 'N', '0', 'admin', sysdate(), '', null, '黄金等级');
insert into sys_dict_data values(239, 4,  '钻石', '4', 'erp_customer_level', '', 'danger',  'N', '0', 'admin', sysdate(), '', null, '钻石等级');

insert into sys_dict_data values(240, 1,  '原材料仓', '1', 'erp_warehouse_type', '', '', 'N', '0', 'admin', sysdate(), '', null, '原材料仓');
insert into sys_dict_data values(241, 2,  '半成品仓', '2', 'erp_warehouse_type', '', '', 'N', '0', 'admin', sysdate(), '', null, '半成品仓');
insert into sys_dict_data values(242, 3,  '成品仓',   '3', 'erp_warehouse_type', '', '', 'N', '0', 'admin', sysdate(), '', null, '成品仓');
insert into sys_dict_data values(243, 4,  '周转仓',   '4', 'erp_warehouse_type', '', '', 'N', '0', 'admin', sysdate(), '', null, '周转仓');

insert into sys_dict_data values(244, 1,  '采购入库', '1', 'erp_stock_record_type', '', '', 'N', '0', 'admin', sysdate(), '', null, '采购入库');
insert into sys_dict_data values(245, 2,  '销售出库', '2', 'erp_stock_record_type', '', '', 'N', '0', 'admin', sysdate(), '', null, '销售出库');
insert into sys_dict_data values(246, 3,  '生产入库', '3', 'erp_stock_record_type', '', '', 'N', '0', 'admin', sysdate(), '', null, '生产入库');
insert into sys_dict_data values(247, 4,  '生产领料', '4', 'erp_stock_record_type', '', '', 'N', '0', 'admin', sysdate(), '', null, '生产领料');
insert into sys_dict_data values(248, 5,  '盘盈入库', '5', 'erp_stock_record_type', '', '', 'N', '0', 'admin', sysdate(), '', null, '盘盈入库');
insert into sys_dict_data values(249, 6,  '盘亏出库', '6', 'erp_stock_record_type', '', '', 'N', '0', 'admin', sysdate(), '', null, '盘亏出库');
insert into sys_dict_data values(250, 7,  '调拨入库', '7', 'erp_stock_record_type', '', '', 'N', '0', 'admin', sysdate(), '', null, '调拨入库');
insert into sys_dict_data values(251, 8,  '调拨出库', '8', 'erp_stock_record_type', '', '', 'N', '0', 'admin', sysdate(), '', null, '调拨出库');

insert into sys_dict_data values(252, 1,  '未盘点', '0', 'erp_check_status', '', 'info',    'N', '0', 'admin', sysdate(), '', null, '未盘点');
insert into sys_dict_data values(253, 2,  '已盘点', '1', 'erp_check_status', '', 'success', 'N', '0', 'admin', sysdate(), '', null, '已盘点');

insert into sys_dict_data values(254, 1,  '收款', '1', 'erp_payment_type', '', 'success', 'N', '0', 'admin', sysdate(), '', null, '收款');
insert into sys_dict_data values(255, 2,  '付款', '2', 'erp_payment_type', '', 'warning', 'N', '0', 'admin', sysdate(), '', null, '付款');

insert into sys_dict_data values(256, 1,  '未结清',   '0', 'erp_settle_status', '', 'info',    'N', '0', 'admin', sysdate(), '', null, '未结清');
insert into sys_dict_data values(257, 2,  '部分结清', '1', 'erp_settle_status', '', 'warning', 'N', '0', 'admin', sysdate(), '', null, '部分结清');
insert into sys_dict_data values(258, 3,  '已结清',   '2', 'erp_settle_status', '', 'success', 'N', '0', 'admin', sysdate(), '', null, '已结清');

insert into sys_dict_data values(259, 1,  '未开始', '0', 'erp_production_status', '', 'info',    'N', '0', 'admin', sysdate(), '', null, '未开始');
insert into sys_dict_data values(260, 2,  '生产中', '1', 'erp_production_status', '', 'warning', 'N', '0', 'admin', sysdate(), '', null, '生产中');
insert into sys_dict_data values(261, 3,  '已完工', '2', 'erp_production_status', '', 'success', 'N', '0', 'admin', sysdate(), '', null, '已完工');
insert into sys_dict_data values(262, 4,  '已关闭', '3', 'erp_production_status', '', 'danger',  'N', '0', 'admin', sysdate(), '', null, '已关闭');

insert into sys_dict_data values(263, 1,  '高', '1', 'erp_priority', '', 'danger',  'N', '0', 'admin', sysdate(), '', null, '高优先级');
insert into sys_dict_data values(264, 2,  '中', '2', 'erp_priority', '', 'warning', 'N', '0', 'admin', sysdate(), '', null, '中优先级');
insert into sys_dict_data values(265, 3,  '低', '3', 'erp_priority', '', 'info',    'N', '0', 'admin', sysdate(), '', null, '低优先级');