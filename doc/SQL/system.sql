-- 字典表
CREATE TABLE `sys_dict`
(
    `id`          INTEGER PRIMARY KEY,
    `name`        TEXT NOT NULL,
    `status`      TEXT     DEFAULT 'Enable',
    `code`        TEXT NOT NULL UNIQUE,
    `remark`      TEXT,
    `sort`        NUMERIC,
    `is_default`  BOOLEAN  DEFAULT FALSE,
    `is_delete`   BOOLEAN  DEFAULT FALSE,
    `create_by`   TEXT,
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `update_by`   TEXT,
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP
);


-- 字典值表
CREATE TABLE `sys_dict_item`
(
    `id`          INTEGER PRIMARY KEY,
    `dict_id`     INTEGER NOT NULL,
    `name`        TEXT    NOT NULL,
    `status`      TEXT     DEFAULT 'Enable',
    `code`        TEXT    NOT NULL,
    `value`       TEXT    NOT NULL,
    `style`       TEXT,
    `remark`      TEXT,
    `sort`        NUMERIC,
    `is_default`  BOOLEAN  DEFAULT FALSE,
    `is_delete`   BOOLEAN  DEFAULT FALSE,
    `create_by`   TEXT,
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `update_by`   TEXT,
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX `idx_dict_id_code` ON `sys_dict_item` (`dict_id`, `code`);


-- 用户表
CREATE TABLE `sys_user`
(
    `id`          INTEGER PRIMARY KEY,
    `avatar`      TEXT,
    `name`        TEXT NOT NULL,
    `status`      TEXT     DEFAULT 'Enable',
    `account`     TEXT NOT NULL UNIQUE,
    `password`    TEXT NOT NULL,
    `phone`       TEXT,
    `email`       TEXT,
    `remark`      TEXT,
    `is_default`  BOOLEAN  DEFAULT FALSE,
    `is_delete`   BOOLEAN  DEFAULT FALSE,
    `create_by`   TEXT,
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `update_by`   TEXT,
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP
);


-- 角色表
CREATE TABLE `sys_role`
(
    `id`          INTEGER PRIMARY KEY,
    `name`        TEXT NOT NULL UNIQUE,
    `status`      TEXT     DEFAULT 'Enable',
    `remark`      TEXT,
    `is_default`  BOOLEAN  DEFAULT FALSE,
    `create_by`   TEXT,
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `update_by`   TEXT,
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP
);


-- 权限表
CREATE TABLE `sys_permiss`
(
    `id`          INTEGER PRIMARY KEY,
    `sup_id`      INTEGER  DEFAULT 0,
    `icon`        TEXT,
    `name`        TEXT NOT NULL UNIQUE,
    `type`        TEXT NOT NULL,
    `status`      TEXT     DEFAULT 'Enable',
    `code`        TEXT NOT NULL UNIQUE,
    `remark`      TEXT,
    `create_by`   TEXT,
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `update_by`   TEXT,
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- 角色权限表
CREATE TABLE `sys_role_permiss`
(
    `rode_id`    INTEGER NOT NULL,
    `permiss_id` INTEGER NOT NULL
);


CREATE
TANLE `sys_cache`(
    `scope` TEXT,
    `key` TEXT,
    `value` TEXT,
    `expire` INTEGER

)