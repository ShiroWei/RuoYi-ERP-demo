-- ============================================================
-- ERP 演示数据（2026-08-19）
-- 前置：已导入 ry_20260417.sql、ry_config_20260611.sql、ry_erp_20260818.sql
-- 说明：档案 + 采购/销售/库存/财务/生产全流程演示单据（含审核流示例）
-- 导入：mysql -uroot --default-character-set=utf8mb4 ry-cloud < 本文件
-- ============================================================
SET NAMES utf8mb4;

-- 清空已有数据（保证脚本可重复执行）
truncate table erp_production_order;
truncate table erp_bom_item;
truncate table erp_bom;
truncate table erp_payment;
truncate table erp_payable;
truncate table erp_receivable;
truncate table erp_stock_transfer;
truncate table erp_stock_check;
truncate table erp_stock_record;
truncate table erp_stock;
truncate table erp_sale_return;
truncate table erp_sale_outbound;
truncate table erp_sale_order_item;
truncate table erp_sale_order;
truncate table erp_purchase_return;
truncate table erp_purchase_inbound;
truncate table erp_purchase_order_item;
truncate table erp_purchase_order;
truncate table erp_warehouse;
truncate table erp_customer;
truncate table erp_supplier;
truncate table erp_material;

-- ============================================================
-- 一、基础档案
-- ============================================================

-- 1. 物料档案
insert into erp_material (material_id, material_code, material_name, category, specification, unit, purchase_price, sale_price, safe_stock, status, remark, create_by, create_time, update_by, update_time, del_flag) values
(1, 'M1001', '原材料-钢板',   '原材料', 'Q235B 2mm*1250mm', '吨', 4200.00, 5200.00, 50.00,   '0', '主结构钢材',  'admin', sysdate(), '', null, '0'),
(2, 'M1002', '电子元器件',    '原材料', 'STM32F103C8T6',   '个', 12.00,   25.00,   500.00,  '0', '主控芯片',    'admin', sysdate(), '', null, '0'),
(3, 'M2001', '半成品-电机组件', '半成品', 'DC24V-300W',     '台', 380.00,  580.00,  100.00,  '0', '电机组件',    'admin', sysdate(), '', null, '0'),
(4, 'M3001', '成品-产品A',    '成品',   '标准款',           '台', 680.00,  980.00,  80.00,   '0', '标准款成品',  'admin', sysdate(), '', null, '0'),
(5, 'M3002', '成品-产品B',    '成品',   '增强款',           '台', 890.00,  1280.00, 60.00,   '0', '增强款成品',  'admin', sysdate(), '', null, '0'),
(6, 'M4001', '包装纸箱',      '辅料',   '60*40*40cm',      '个', 3.00,    8.00,    1000.00, '0', '外包装箱',    'admin', sysdate(), '', null, '0');

-- 2. 供应商档案
insert into erp_supplier (supplier_id, supplier_code, supplier_name, supplier_type, contact_person, phone, email, address, tax_no, bank_name, bank_account, status, remark, create_by, create_time, update_by, update_time, del_flag) values
(1, 'SUP-001', '华宇钢铁有限公司',   '原材料供应商', '张伟', '13800000001', 'zhangwei@huayu.com', '上海市宝山区友谊路1号', '91310113MA1GM00001', '中国工商银行宝山支行', '6222021001000001', '0', '长期合作钢材供应商', 'admin', sysdate(), '', null, '0'),
(2, 'SUP-002', '华鑫电子科技有限公司', '电子元器件供应商', '李娜', '13800000002', 'lina@huaxin.com', '深圳市南山区科技园路2号', '91440300MA5D000002', '中国银行南山支行', '6216601001000002', '0', '主控芯片供应商', 'admin', sysdate(), '', null, '0'),
(3, 'SUP-003', '中电电机有限公司',   '半成品供应商', '王强', '13800000003', 'wangqiang@zhongdian.com', '南京市江宁区将军大道3号', '91320115MA1E000003', '中国建设银行江宁支行', '6217001001000003', '0', '电机组件供应商', 'admin', sysdate(), '', null, '0'),
(4, 'SUP-004', '恒达包装有限公司',   '辅料供应商', '刘洋', '13800000004', 'liuyang@hengda.com', '苏州市吴中区金枫路4号', '91320506MA1F000004', '中国农业银行吴中支行', '6228481001000004', '0', '包装纸箱供应商', 'admin', sysdate(), '', null, '0');

-- 3. 客户档案
insert into erp_customer (customer_id, customer_code, customer_name, customer_type, customer_level, contact_person, phone, email, address, credit_limit, status, remark, create_by, create_time, update_by, update_time, del_flag) values
(1, 'CUS-001', '华东机械制造有限公司',   '终端客户', 'VIP',   '陈杰', '13900000001', 'chenjie@huadong.com', '杭州市萧山区建设一路1号', 1000000.00, '0', '重点客户', 'admin', sysdate(), '', null, '0'),
(2, 'CUS-002', '华南智能装备有限公司',   '终端客户', '普通', '林芳', '13900000002', 'linfang@huanan.com', '广州市黄埔区科学城2号', 800000.00,  '0', '华南区域客户', 'admin', sysdate(), '', null, '0'),
(3, 'CUS-003', '北方重工集团有限公司',   '终端客户', 'VIP',   '赵磊', '13900000003', 'zhaolei@beifang.com', '沈阳市铁西区重工街3号', 1500000.00, '0', '重点客户', 'admin', sysdate(), '', null, '0'),
(4, 'CUS-004', '西部矿山设备有限公司',   '终端客户', '普通', '孙丽', '13900000004', 'sunli@xibu.com', '成都市高新区天府大道4号', 600000.00,  '0', '西南区域客户', 'admin', sysdate(), '', null, '0');

-- 4. 仓库档案
insert into erp_warehouse (warehouse_id, warehouse_code, warehouse_name, warehouse_type, manager, phone, address, status, remark, create_by, create_time, update_by, update_time, del_flag) values
(1, 'WH-001', '原料仓',   '原材料仓', '周军', '13700000001', '厂区A栋1层', '0', '原材料存放', 'admin', sysdate(), '', null, '0'),
(2, 'WH-002', '半成品仓', '半成品仓', '吴敏', '13700000002', '厂区A栋2层', '0', '半成品存放', 'admin', sysdate(), '', null, '0'),
(3, 'WH-003', '成品仓',   '成品仓',   '郑华', '13700000003', '厂区B栋1层', '0', '成品存放',   'admin', sysdate(), '', null, '0');

-- ============================================================
-- 二、采购业务
-- ============================================================

-- 5. 采购订单（status: 0草稿 1待审 2审核通过 3已驳回 4已完成）
insert into erp_purchase_order (order_id, order_no, supplier_id, order_date, total_amount, status, remark, create_by, create_time, update_by, update_time) values
(1, 'CG20260702001', 3, '2026-07-02 10:00:00', 38000.00,  '4', '2026年7月电机组件采购', 'admin', sysdate(), '', null),
(2, 'CG20260715001', 4, '2026-07-15 10:00:00', 6000.00,   '4', '包装纸箱采购',       'admin', sysdate(), '', null),
(3, 'CG20260801001', 1, '2026-08-01 10:00:00', 336000.00, '2', '钢板备料采购',       'admin', sysdate(), '', null),
(4, 'CG20260805001', 2, '2026-08-05 10:00:00', 24000.00,  '2', '主控芯片采购',       'admin', sysdate(), '', null);

insert into erp_purchase_order_item (item_id, order_id, material_id, material_code, material_name, specification, unit, quantity, price, amount, remark) values
(1, 1, 3, 'M2001', '半成品-电机组件', 'DC24V-300W',    '台', 100.00,  380.00,  38000.00,  ''),
(2, 2, 6, 'M4001', '包装纸箱',        '60*40*40cm',   '个', 2000.00, 3.00,    6000.00,   ''),
(3, 3, 1, 'M1001', '原材料-钢板',      'Q235B 2mm*1250mm', '吨', 80.00, 4200.00, 336000.00, ''),
(4, 4, 2, 'M1002', '电子元器件',       'STM32F103C8T6', '个', 2000.00, 12.00,   24000.00,  '');

-- 6. 采购入库单
insert into erp_purchase_inbound (inbound_id, inbound_no, order_id, supplier_id, warehouse_id, inbound_date, total_amount, status, remark, create_by, create_time, update_by, update_time) values
(1, 'RK20260806001', 4, 2, 1, '2026-08-06 14:00:00', 24000.00,  '4', '芯片入库完成',   'admin', sysdate(), '', null),
(2, 'RK20260811001', 3, 1, 1, '2026-08-11 14:00:00', 336000.00, '4', '钢板入库完成',   'admin', sysdate(), '', null),
(3, 'RK20260818001', 1, 3, 1, '2026-08-18 09:00:00', 38000.00,  '1', '电机组件入库待审', 'admin', sysdate(), '', null);

-- 7. 采购退货单
insert into erp_purchase_return (return_id, return_no, order_id, supplier_id, warehouse_id, return_date, total_amount, status, reason, remark, create_by, create_time, update_by, update_time) values
(1, 'TH20260812001', 3, 1, 1, '2026-08-12 15:00:00', 21000.00, '2', '板材厚度偏差，退货5吨', '质量不合格', 'admin', sysdate(), '', null);

-- ============================================================
-- 三、销售业务
-- ============================================================

-- 8. 销售订单
insert into erp_sale_order (order_id, order_no, customer_id, order_date, total_amount, status, remark, create_by, create_time, update_by, update_time) values
(1, 'XS20260705001', 3, '2026-07-05 10:00:00', 117600.00, '4', '北方重工年度订单1', 'admin', sysdate(), '', null),
(2, 'XS20260720001', 4, '2026-07-20 10:00:00', 102400.00, '4', '西部矿山订单',     'admin', sysdate(), '', null),
(3, 'XS20260808001', 1, '2026-08-08 10:00:00', 98000.00,  '2', '华东机械标准款',   'admin', sysdate(), '', null),
(4, 'XS20260815001', 2, '2026-08-15 10:00:00', 76800.00,  '2', '华南智能增强款',   'admin', sysdate(), '', null),
(5, 'XS20260818001', 1, '2026-08-18 10:00:00', 49000.00,  '1', '待审核销售单',     'admin', sysdate(), '', null);

insert into erp_sale_order_item (item_id, order_id, material_id, material_code, material_name, specification, unit, quantity, price, amount, remark) values
(1, 1, 4, 'M3001', '成品-产品A', '标准款', '台', 120.00, 980.00,  117600.00, ''),
(2, 2, 5, 'M3002', '成品-产品B', '增强款', '台', 80.00,  1280.00, 102400.00, ''),
(3, 3, 4, 'M3001', '成品-产品A', '标准款', '台', 100.00, 980.00,  98000.00,  ''),
(4, 4, 5, 'M3002', '成品-产品B', '增强款', '台', 60.00,  1280.00, 76800.00,  ''),
(5, 5, 4, 'M3001', '成品-产品A', '标准款', '台', 50.00,  980.00,  49000.00,  '');

-- 9. 销售出库单
insert into erp_sale_outbound (outbound_id, outbound_no, order_id, customer_id, warehouse_id, outbound_date, total_amount, status, remark, create_by, create_time, update_by, update_time) values
(1, 'CK20260809001', 3, 1, 3, '2026-08-09 10:00:00', 98000.00,  '4', '华东机械出货', 'admin', sysdate(), '', null),
(2, 'CK20260816001', 4, 2, 3, '2026-08-16 10:00:00', 76800.00,  '4', '华南智能出货', 'admin', sysdate(), '', null);

-- 10. 销售退货单
insert into erp_sale_return (return_id, return_no, order_id, customer_id, warehouse_id, return_date, total_amount, status, reason, remark, create_by, create_time, update_by, update_time) values
(1, 'XSTH20260810001', 3, 1, 3, '2026-08-10 15:00:00', 9800.00, '2', '客户退换10台标准款', '客户退换', 'admin', sysdate(), '', null);

-- ============================================================
-- 四、库存业务
-- ============================================================

-- 11. 当前库存（warehouse_id + material_id 唯一）
insert into erp_stock (stock_id, warehouse_id, material_id, quantity, create_by, create_time, update_by, update_time) values
(1, 1, 1, 60.00,   'admin', sysdate(), '', null),
(2, 1, 2, 3000.00, 'admin', sysdate(), '', null),
(3, 1, 6, 500.00,  'admin', sysdate(), '', null),
(4, 2, 3, 80.00,   'admin', sysdate(), '', null),
(5, 3, 4, 100.00,  'admin', sysdate(), '', null),
(6, 3, 5, 40.00,   'admin', sysdate(), '', null);

-- 12. 出入库流水（近7日趋势用）
insert into erp_stock_record (record_id, record_no, record_type, direction, warehouse_id, material_id, quantity, biz_no, operator, record_date, remark, create_by, create_time, update_by, update_time) values
(1, 'CR20260813001', '采购入库', '1', 1, 1, 20.00,  'RK20260811001', 'admin', '2026-08-13 09:00:00', '', 'admin', sysdate(), '', null),
(2, 'CR20260814001', '采购入库', '1', 1, 2, 500.00, 'RK20260806001', 'admin', '2026-08-14 09:00:00', '', 'admin', sysdate(), '', null),
(3, 'CC20260815001', '销售出库', '0', 3, 4, 100.00, 'CK20260809001', 'admin', '2026-08-15 09:00:00', '', 'admin', sysdate(), '', null),
(4, 'CC20260816001', '销售出库', '0', 3, 5, 60.00,  'CK20260816001', 'admin', '2026-08-16 09:00:00', '', 'admin', sysdate(), '', null),
(5, 'CT20260817001', '生产领用', '0', 2, 3, 30.00,  'SC20260805001', 'admin', '2026-08-17 09:00:00', '', 'admin', sysdate(), '', null);

-- 13. 库存盘点（单行盘点）
insert into erp_stock_check (check_id, check_no, warehouse_id, material_id, book_qty, actual_qty, diff_qty, check_date, status, remark, create_by, create_time, update_by, update_time) values
(1, 'PD20260818001', 1, 1, 60.00,   60.00,   0.00,   '2026-08-18 10:00:00', '1', '盘盈盘亏正常', 'admin', sysdate(), '', null),
(2, 'PD20260818002', 1, 2, 3000.00, 2980.00, -20.00, '2026-08-18 10:00:00', '1', '盘亏20个',     'admin', sysdate(), '', null),
(3, 'PD20260818003', 3, 4, 100.00,  100.00,  0.00,   '2026-08-18 10:00:00', '1', '盘盈盘亏正常', 'admin', sysdate(), '', null);

-- 14. 库存调拨（含审核流）
insert into erp_stock_transfer (transfer_id, transfer_no, from_warehouse_id, to_warehouse_id, material_id, quantity, transfer_date, status, remark, create_by, create_time, update_by, update_time) values
(1, 'DB20260812001', 1, 2, 1, 10.00, '2026-08-12 10:00:00', '2', '原料转半成品仓',     'admin', sysdate(), '', null),
(2, 'DB20260818001', 2, 3, 3, 20.00, '2026-08-18 10:00:00', '1', '电机组件转成品仓待审', 'admin', sysdate(), '', null);

-- ============================================================
-- 五、财务业务
-- ============================================================

-- 15. 应收款（status: 0未结清 1部分结清 2已结清）
insert into erp_receivable (receivable_id, bill_no, bill_type, customer_id, amount, received_amount, balance, due_date, status, remark, create_by, create_time, update_by, update_time) values
(1, 'XS20260705001', '销售订单', 3, 117600.00, 117600.00, 0.00,    '2026-08-10 00:00:00', '2', '已结清',   'admin', sysdate(), '', null),
(2, 'XS20260808001', '销售订单', 1, 98000.00,  0.00,      98000.00, '2026-08-20 00:00:00', '0', '未收款',   'admin', sysdate(), '', null),
(3, 'XS20260815001', '销售订单', 2, 76800.00,  30000.00,  46800.00, '2026-08-25 00:00:00', '1', '部分收款', 'admin', sysdate(), '', null);

-- 16. 应付款
insert into erp_payable (payable_id, bill_no, bill_type, supplier_id, amount, paid_amount, balance, due_date, status, remark, create_by, create_time, update_by, update_time) values
(1, 'CG20260702001', '采购订单', 3, 38000.00,  0.00,      38000.00,  '2026-07-15 00:00:00', '0', '未付款',   'admin', sysdate(), '', null),
(2, 'CG20260801001', '采购订单', 1, 336000.00, 336000.00, 0.00,      '2026-08-11 00:00:00', '2', '已结清',   'admin', sysdate(), '', null),
(3, 'CG20260805001', '采购订单', 2, 24000.00,  10000.00,  14000.00,  '2026-08-12 00:00:00', '1', '部分付款', 'admin', sysdate(), '', null);

-- 17. 收付款单（payment_type: 1收款 2付款；partner_type: 客户/供应商）
insert into erp_payment (payment_id, payment_no, payment_type, bill_no, partner_type, partner_id, amount, payment_date, status, remark, create_by, create_time, update_by, update_time) values
(1, 'SK20260812001', '1', 'XS20260705001', '客户',    3, 117600.00, '2026-08-12 10:00:00', '4', '收到北方重工货款', 'admin', sysdate(), '', null),
(2, 'FK20260813001', '2', 'CG20260801001', '供应商',  1, 336000.00, '2026-08-13 10:00:00', '4', '支付华宇钢铁货款', 'admin', sysdate(), '', null),
(3, 'SK20260818001', '1', 'XS20260815001', '客户',    2, 30000.00,  '2026-08-18 10:00:00', '2', '华南智能预收',     'admin', sysdate(), '', null);

-- ============================================================
-- 六、生产业务
-- ============================================================

-- 18. BOM 主表（product_id 引用物料档案中的成品）
insert into erp_bom (bom_id, bom_no, product_id, product_code, product_name, unit, status, remark, create_by, create_time, update_by, update_time) values
(1, 'BOM-A001', 4, 'M3001', '成品-产品A', '台', '0', '标准款BOM', 'admin', sysdate(), '', null),
(2, 'BOM-B001', 5, 'M3002', '成品-产品B', '台', '0', '增强款BOM', 'admin', sysdate(), '', null);

-- 19. BOM 明细
insert into erp_bom_item (item_id, bom_id, material_id, material_code, material_name, specification, unit, quantity, remark) values
(1, 1, 1, 'M1001', '原材料-钢板',   'Q235B 2mm*1250mm', '吨', 2.00,  ''),
(2, 1, 3, 'M2001', '半成品-电机组件', 'DC24V-300W',     '台', 1.00,  ''),
(3, 1, 6, 'M4001', '包装纸箱',      '60*40*40cm',      '个', 10.00, ''),
(4, 2, 1, 'M1001', '原材料-钢板',   'Q235B 2mm*1250mm', '吨', 2.50,  ''),
(5, 2, 3, 'M2001', '半成品-电机组件', 'DC24V-300W',     '台', 1.00,  ''),
(6, 2, 6, 'M4001', '包装纸箱',      '60*40*40cm',      '个', 12.00, '');

-- 20. 生产工单（status: 0未开始 1生产中 2已完工 3已关闭）
insert into erp_production_order (order_id, order_no, product_id, product_code, product_name, plan_qty, finish_qty, order_date, plan_start_date, plan_end_date, status, remark, create_by, create_time, update_by, update_time) values
(1, 'SC20260701001', 4, 'M3001', '成品-产品A', 120.00, 120.00, '2026-07-01 09:00:00', '2026-07-01 00:00:00', '2026-07-10 00:00:00', '2', '已完工', 'admin', sysdate(), '', null),
(2, 'SC20260805001', 4, 'M3001', '成品-产品A', 100.00, 0.00,   '2026-08-05 09:00:00', '2026-08-05 00:00:00', '2026-08-20 00:00:00', '1', '生产中', 'admin', sysdate(), '', null),
(3, 'SC20260810001', 5, 'M3002', '成品-产品B', 80.00,  80.00,  '2026-08-10 09:00:00', '2026-08-10 00:00:00', '2026-08-16 00:00:00', '2', '已完工', 'admin', sysdate(), '', null);

-- ============================================================
-- 七、辅助数据
-- ============================================================

-- 21. 通知公告（工作台「最新公告」使用）
insert into sys_notice (notice_title, notice_type, notice_content, status, create_by, create_time, update_by, update_time, remark) values
('ERP 演示环境上线通知', '1', 'ERP 采购、销售、库存、财务、生产全流程演示环境已就绪，可使用 admin/admin123 登录体验。', '0', 'admin', sysdate(), '', null, null);