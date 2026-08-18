<p align="center">
	<img alt="logo" src="https://oscimg.oschina.net/oscnet/up-b99b286755aef70355a7084753f89cdb7c9.png">
</p>
<h1 align="center" style="margin: 30px 0 30px; font-weight: bold;">ERP 企业管理平台</h1>
<h4 align="center">基于 RuoYi-Cloud 二次开发的进销存生产一体化管理系统（演示项目）</h4>
<p align="center">
	<a href="https://github.com/ShiroWei/RuoYi-ERP-demo"><img src="https://img.shields.io/badge/RuoYi--Cloud-3.6.8-brightgreen.svg"></a>
	<a href="https://github.com/ShiroWei/RuoYi-ERP-demo/blob/master/LICENSE"><img src="https://img.shields.io/github/license/mashape/apistatus.svg"></a>
</p>

## 平台简介

ERP 企业管理平台是一套**演示性质**的企业资源计划（ERP）管理系统，覆盖**基础资料、采购、销售、库存、财务、生产**六大核心业务域。项目在开源框架 [RuoYi-Cloud v3.6.8](https://gitee.com/y_project/RuoYi-Cloud)（Spring Boot 4.1.0 / Spring Cloud Alibaba / Vue 2 + Element UI）基础上二次开发。

* 六大业务域完整页面：物料/供应商/客户/仓库、采购订单/入库/退货、销售订单/出库/退货、库存台账/出入库/盘点/调拨、应收/应付/收付款、BOM/生产工单。
* **Mock 数据优先**：所有业务接口均为前端演示数据（内存数组，支持完整增删改查），并预留真实接口注释，接入后端时取消注释即可。
* ERP 工作台首页：经营统计卡片、待审单据、库存预警、经营趋势图表。
* 配套 SQL 脚本：22 张业务表 DDL、ERP 菜单（基础资料/采购/销售/库存/财务/生产）、字典、角色授权，一键导入。

## 功能特性

| 业务域 | 功能模块 | 说明 |
| :--- | :--- | :--- |
| 工作台 | ERP 工作台 | 统计卡片、待审单据、库存预警、经营图表、快捷入口 |
| 基础资料 | 物料管理 | 物料编码/分类/规格/单价/安全库存 |
| 基础资料 | 供应商管理 | 供应商档案/类型/联系人/银行信息 |
| 基础资料 | 客户管理 | 客户档案/等级/信用额度 |
| 基础资料 | 仓库管理 | 仓库档案/类型/负责人 |
| 采购管理 | 采购订单 | 订单头 + 明细行（物料/数量/单价自动算金额） |
| 采购管理 | 采购入库 | 入库单 + 明细，关联仓库 |
| 采购管理 | 采购退货 | 退货单 + 明细 |
| 销售管理 | 销售订单 | 订单头 + 明细行 |
| 销售管理 | 销售出库 | 出库单 + 明细，关联仓库 |
| 销售管理 | 销售退货 | 退货单 + 明细 |
| 库存管理 | 库存查询 | 实时库存台账，安全库存预警高亮 |
| 库存管理 | 出入库记录 | 采购入库/销售出库/生产领料/调拨等类型 |
| 库存管理 | 库存盘点 | 盘点单 + 账实对比自动计算盈亏 |
| 库存管理 | 库存调拨 | 仓库间调拨 |
| 财务管理 | 应收账款 | 应收/已收/未收金额、结清状态 |
| 财务管理 | 应付账款 | 应付/已付/未付金额、结清状态 |
| 财务管理 | 收付款管理 | 收款/付款流水登记 |
| 生产管理 | BOM 物料清单 | 产成品 + 单耗用量清单，多版本 |
| 生产管理 | 生产工单 | 工单计划/优先级/状态/领料清单 |

## 技术栈

| 组件 | 版本 |
| :--- | :--- |
| JDK | 24 |
| Spring Boot | 4.1.0 |
| Spring Cloud | 2025.1.2 |
| Spring Cloud Alibaba | 2025.1.0.0 |
| Nacos | 3.x |
| Redis | 最新稳定版 |
| Vue | 2.x |
| Element UI | 2.15.x |
| MySQL | 8.x |

## 演示账号

| 账号 | 密码 | 角色 |
| :--- | :--- | :--- |
| admin | admin123 | 管理员 |

## 本地开发环境

环境要求：JDK 24+、Maven 3.9+、Node 16/18/20、MySQL 8、Redis、Nacos 3.x。

1. 导入数据库脚本（先建库 `ry-cloud`）：`sql/ry_20260417.sql`、`sql/ry_config_20260611.sql`、`sql/ry_erp_20260818.sql`。
2. 启动 Nacos（3.0.2，默认端口 8848）。
3. 依次启动后端微服务：`ruoyi-gateway`(8000) → `ruoyi-auth` → `ruoyi-modules-system` → `ruoyi-modules-job` 等。
4. 前端：`cd ruoyi-ui && npm install && npm run dev`（默认端口 80，代理指向网关 8000）。

详细说明见 [docs/本地开发环境配置.md](docs/本地开发环境配置.md)。

## 目录结构

```
ruoyi-ui/                  # 前端（Vue 2 + Element UI）
  src/api/erp/             # ERP mock API（base/purchase/sale/stock/finance/production）
  src/views/erp/           # ERP 页面（base/purchase/sale/stock/finance/production）
  src/views/dashboard/     # ERP 工作台
ruoyi-gateway/             # 网关（端口 8000）
ruoyi-auth/                # 认证中心
ruoyi-modules-system/      # 系统模块
sql/                       # 数据库脚本（含 ERP 业务表/菜单/字典）
docs/                      # 本地开发环境配置文档
```

## 上游来源

本项目基于 [RuoYi-Cloud v3.6.8](https://gitee.com/y_project/RuoYi-Cloud) 二次开发，遵循 MIT License，保留上游完整提交历史。若依官方文档见 [RuoYi 官网](https://ruoyi.vip)。
