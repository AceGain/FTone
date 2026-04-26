# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## 项目概述

FTone (tone-tiny) 是一个 Spring Boot 轻量级基础框架，用于快速开发 Web 应用。

**技术栈：** Spring Boot 3.5 + MyBatis-Flex + SQLite + Spring Security + JWT

**Java 版本：** 17

## 常用命令

```bash
# 编译项目
mvn compile

# 打包 (跳过测试)
mvn package

# 运行
mvn spring-boot:run

# 清理并打包
mvn clean package
```

## 架构结构

```
cn.acegain.tone
├── base/           # 基础配置层
│   ├── security/   # Spring Security + JWT 认证
│   ├── jwt/        # JWT 服务
│   └── captcha/    # 验证码配置
├── common/         # 公共组件
│   ├── Result      # 统一响应封装
│   ├── api/        # API 基类
│   └── entity/     # 实体基类
└── system/         # 系统模块（用户、认证等）
```

## 核心设计

### API 层继承体系

- `BaseApi`：提供 `getRequest()` 和 `getResponse()` 方法
- `WebApi<T, S>`：通用 CRUD 控制器，提供 `save`、`removeById`、`updateById`、`list`、`page`、`info` 方法

### 安全认证

- 无状态 Session (STATELESS)
- JWT Token 认证
- 公开接口：`GET /`、`GET /info`、`GET /captcha`、`POST /auth`
- 其他接口需要认证

### 数据库

- SQLite 数据库文件：`db/tone.db`
- ORM：MyBatis-Flex
- 主键策略：`flexId` (前置生成)

## 配置说明

应用配置文件：`src/main/resources/application.yaml`

- 服务端口：5221
- 上下文路径：`/api`
- JWT 配置在 `tone.jwt` 节点
- 验证码配置在 `tone.captcha` 节点
