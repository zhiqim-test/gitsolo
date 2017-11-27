-- ---------------------------------------------------
-- 知启蒙代码仓库独立版功能菜单
-- ---------------------------------------------------


truncate table ZMR_MENU;
insert into ZMR_MENU (PARENT_CODE, MENU_CODE, MENU_NAME, MENU_NAME_ABBR, MENU_LEVEL, MENU_STATUS, MENU_TYPE, MENU_ICON, MENU_URL, MENU_DESC) values ('ROOT', 'MENU', '系统功能根菜单', '', 0, 0, 0, '', '', '');
insert into ZMR_MENU (PARENT_CODE, MENU_CODE, MENU_NAME, MENU_NAME_ABBR, MENU_LEVEL, MENU_STATUS, MENU_TYPE, MENU_ICON, MENU_URL, MENU_DESC) values ('MENU', 'MENU_010', '系统配置', '系统', 1, 0, 1, 'z-config', '/config.htm', '');
insert into ZMR_MENU (PARENT_CODE, MENU_CODE, MENU_NAME, MENU_NAME_ABBR, MENU_LEVEL, MENU_STATUS, MENU_TYPE, MENU_ICON, MENU_URL, MENU_DESC) values ('MENU_010', 'MENU_010_010', '系统配置设置', '系统设置', 2, 0, 1, 'z-setting', '/config.htm', '');
insert into ZMR_MENU (PARENT_CODE, MENU_CODE, MENU_NAME, MENU_NAME_ABBR, MENU_LEVEL, MENU_STATUS, MENU_TYPE, MENU_ICON, MENU_URL, MENU_DESC) values ('MENU_010', 'MENU_010_020', '系统参数设置', '系统设置', 2, 0, 1, 'z-modify', '/param.htm', '');
insert into ZMR_MENU (PARENT_CODE, MENU_CODE, MENU_NAME, MENU_NAME_ABBR, MENU_LEVEL, MENU_STATUS, MENU_TYPE, MENU_ICON, MENU_URL, MENU_DESC) values ('MENU_010_020', 'MENU_010_020_010', '首页主题设置', '', 3, 0, 2, '', '/themeIndex.htm', '');
insert into ZMR_MENU (PARENT_CODE, MENU_CODE, MENU_NAME, MENU_NAME_ABBR, MENU_LEVEL, MENU_STATUS, MENU_TYPE, MENU_ICON, MENU_URL, MENU_DESC) values ('MENU_010_020', 'MENU_010_020_020', '主页主题设置', '', 3, 0, 2, '', '/themeMain.htm', '');
insert into ZMR_MENU (PARENT_CODE, MENU_CODE, MENU_NAME, MENU_NAME_ABBR, MENU_LEVEL, MENU_STATUS, MENU_TYPE, MENU_ICON, MENU_URL, MENU_DESC) values ('MENU_010', 'MENU_010_030', '系统菜单管理', '菜单管理', 2, 0, 1, 'z-list', '/menu.htm', '');
insert into ZMR_MENU (PARENT_CODE, MENU_CODE, MENU_NAME, MENU_NAME_ABBR, MENU_LEVEL, MENU_STATUS, MENU_TYPE, MENU_ICON, MENU_URL, MENU_DESC) values ('MENU_010_030', 'MENU_010_030_020', '修改系统菜单', '', 3, 0, 2, '', '/menuModify.htm,/menuUpdate.htm', '');
insert into ZMR_MENU (PARENT_CODE, MENU_CODE, MENU_NAME, MENU_NAME_ABBR, MENU_LEVEL, MENU_STATUS, MENU_TYPE, MENU_ICON, MENU_URL, MENU_DESC) values ('MENU_010', 'MENU_010_040', '系统头像管理', '头像管理', 2, 0, 1, 'z-picture', '/avatar.htm', '');
insert into ZMR_MENU (PARENT_CODE, MENU_CODE, MENU_NAME, MENU_NAME_ABBR, MENU_LEVEL, MENU_STATUS, MENU_TYPE, MENU_ICON, MENU_URL, MENU_DESC) values ('MENU_010_040', 'MENU_010_040_010', '增加系统头像', '', 3, 0, 2, '', '/avatarAdd.htm,/avatarInsert.htm', '');
insert into ZMR_MENU (PARENT_CODE, MENU_CODE, MENU_NAME, MENU_NAME_ABBR, MENU_LEVEL, MENU_STATUS, MENU_TYPE, MENU_ICON, MENU_URL, MENU_DESC) values ('MENU_010_040', 'MENU_010_040_020', '修改系统头像', '', 3, 0, 2, '', '/avatarModify.htm,/avatarUpdate.htm', '');
insert into ZMR_MENU (PARENT_CODE, MENU_CODE, MENU_NAME, MENU_NAME_ABBR, MENU_LEVEL, MENU_STATUS, MENU_TYPE, MENU_ICON, MENU_URL, MENU_DESC) values ('MENU_010_040', 'MENU_010_040_030', '删除系统头像', '', 3, 0, 2, '', '/avatarDelete.htm', '');
insert into ZMR_MENU (PARENT_CODE, MENU_CODE, MENU_NAME, MENU_NAME_ABBR, MENU_LEVEL, MENU_STATUS, MENU_TYPE, MENU_ICON, MENU_URL, MENU_DESC) values ('MENU_010', 'MENU_010_050', '组织部门管理', '部门管理', 2, 0, 1, 'z-apps', '/dept.htm', '');
insert into ZMR_MENU (PARENT_CODE, MENU_CODE, MENU_NAME, MENU_NAME_ABBR, MENU_LEVEL, MENU_STATUS, MENU_TYPE, MENU_ICON, MENU_URL, MENU_DESC) values ('MENU_010_050', 'MENU_010_050_010', '增加部门', '', 3, 0, 1, '', '/deptAdd.htm,/deptInsert.htm', '');
insert into ZMR_MENU (PARENT_CODE, MENU_CODE, MENU_NAME, MENU_NAME_ABBR, MENU_LEVEL, MENU_STATUS, MENU_TYPE, MENU_ICON, MENU_URL, MENU_DESC) values ('MENU_010_050', 'MENU_010_050_020', '修改部门', '', 3, 0, 2, '', '/deptModify.htm,/deptUpdate.htm', '');
insert into ZMR_MENU (PARENT_CODE, MENU_CODE, MENU_NAME, MENU_NAME_ABBR, MENU_LEVEL, MENU_STATUS, MENU_TYPE, MENU_ICON, MENU_URL, MENU_DESC) values ('MENU_010_050', 'MENU_010_050_030', '删除部门', '', 3, 0, 2, '', '/deptDelete.htm', '');
insert into ZMR_MENU (PARENT_CODE, MENU_CODE, MENU_NAME, MENU_NAME_ABBR, MENU_LEVEL, MENU_STATUS, MENU_TYPE, MENU_ICON, MENU_URL, MENU_DESC) values ('MENU_010_050', 'MENU_010_050_040', '部门成员', '', 3, 0, 2, '', '/deptOperator.htm', '');
insert into ZMR_MENU (PARENT_CODE, MENU_CODE, MENU_NAME, MENU_NAME_ABBR, MENU_LEVEL, MENU_STATUS, MENU_TYPE, MENU_ICON, MENU_URL, MENU_DESC) values ('MENU_010_050', 'MENU_010_050_050', '部门权限', '', 3, 0, 2, '', '/deptRule.htm', '');
insert into ZMR_MENU (PARENT_CODE, MENU_CODE, MENU_NAME, MENU_NAME_ABBR, MENU_LEVEL, MENU_STATUS, MENU_TYPE, MENU_ICON, MENU_URL, MENU_DESC) values ('MENU_010', 'MENU_010_110', '操作员管理', '操作员', 2, 0, 1, 'z-customer', '/operator.htm', '');
insert into ZMR_MENU (PARENT_CODE, MENU_CODE, MENU_NAME, MENU_NAME_ABBR, MENU_LEVEL, MENU_STATUS, MENU_TYPE, MENU_ICON, MENU_URL, MENU_DESC) values ('MENU_010_110', 'MENU_010_110_010', '增加操作员', '', 3, 0, 2, '', '/operatorAdd.htm,/operatorInsert.htm', '');
insert into ZMR_MENU (PARENT_CODE, MENU_CODE, MENU_NAME, MENU_NAME_ABBR, MENU_LEVEL, MENU_STATUS, MENU_TYPE, MENU_ICON, MENU_URL, MENU_DESC) values ('MENU_010_110', 'MENU_010_110_020', '修改操作员', '', 3, 0, 2, '', '/operatorModify.htm,/operatorUpdate.htm', '');
insert into ZMR_MENU (PARENT_CODE, MENU_CODE, MENU_NAME, MENU_NAME_ABBR, MENU_LEVEL, MENU_STATUS, MENU_TYPE, MENU_ICON, MENU_URL, MENU_DESC) values ('MENU_010_110', 'MENU_010_110_030', '删除操作员', '', 3, 0, 2, '', '/operatorDelete.htm', '');
insert into ZMR_MENU (PARENT_CODE, MENU_CODE, MENU_NAME, MENU_NAME_ABBR, MENU_LEVEL, MENU_STATUS, MENU_TYPE, MENU_ICON, MENU_URL, MENU_DESC) values ('MENU_010_110', 'MENU_010_110_040', '查看操作员权限', '', 3, 0, 2, '', '/operatorRuleView.htm', '');
insert into ZMR_MENU (PARENT_CODE, MENU_CODE, MENU_NAME, MENU_NAME_ABBR, MENU_LEVEL, MENU_STATUS, MENU_TYPE, MENU_ICON, MENU_URL, MENU_DESC) values ('MENU_010_110', 'MENU_010_110_050', '设置操作员独立权限 ', '', 3, 0, 2, '', '/operatorRule.htm', '');
insert into ZMR_MENU (PARENT_CODE, MENU_CODE, MENU_NAME, MENU_NAME_ABBR, MENU_LEVEL, MENU_STATUS, MENU_TYPE, MENU_ICON, MENU_URL, MENU_DESC) values ('MENU_010_110', 'MENU_010_110_060', '设置操作员部门', '', 3, 0, 2, '', '/operatorDept.htm', '');
insert into ZMR_MENU (PARENT_CODE, MENU_CODE, MENU_NAME, MENU_NAME_ABBR, MENU_LEVEL, MENU_STATUS, MENU_TYPE, MENU_ICON, MENU_URL, MENU_DESC) values ('MENU_010', 'MENU_010_130', '操作日志查询', '操作日志', 2, 0, 1, 'z-text', '/operateLog.htm', '');
insert into ZMR_MENU (PARENT_CODE, MENU_CODE, MENU_NAME, MENU_NAME_ABBR, MENU_LEVEL, MENU_STATUS, MENU_TYPE, MENU_ICON, MENU_URL, MENU_DESC) values ('MENU', 'MENU_020', '项目管理', '项目', 1, 0, 1, 'z-box', '/zhiqimProject/project.htm', '');
insert into ZMR_MENU (PARENT_CODE, MENU_CODE, MENU_NAME, MENU_NAME_ABBR, MENU_LEVEL, MENU_STATUS, MENU_TYPE, MENU_ICON, MENU_URL, MENU_DESC) values ('MENU_020', 'MENU_020_010', '项目信息管理', '项目信息', 2, 0, 1, 'z-text', '/zhiqimProject/project.htm', '');
insert into ZMR_MENU (PARENT_CODE, MENU_CODE, MENU_NAME, MENU_NAME_ABBR, MENU_LEVEL, MENU_STATUS, MENU_TYPE, MENU_ICON, MENU_URL, MENU_DESC) values ('MENU_020', 'MENU_020_020', '项目成员管理', '项目成员', 2, 0, 1, 'z-port', '/zhiqimProject/member.htm', '');
insert into ZMR_MENU (PARENT_CODE, MENU_CODE, MENU_NAME, MENU_NAME_ABBR, MENU_LEVEL, MENU_STATUS, MENU_TYPE, MENU_ICON, MENU_URL, MENU_DESC) values ('MENU_020_010', 'MENU_020_010_010', '增加项目', '增加项目', 3, 0, 2, '', '/zhiqimProject/projectAdd.htm,/zhiqimProject/projectInsert.htm', '');
insert into ZMR_MENU (PARENT_CODE, MENU_CODE, MENU_NAME, MENU_NAME_ABBR, MENU_LEVEL, MENU_STATUS, MENU_TYPE, MENU_ICON, MENU_URL, MENU_DESC) values ('MENU_020_010', 'MENU_020_010_020', '修改项目', '修改项目', 3, 0, 2, '', '/zhiqimProject/projectModify.htm,/zhiqimProject/projectUpdate.htm', '');
insert into ZMR_MENU (PARENT_CODE, MENU_CODE, MENU_NAME, MENU_NAME_ABBR, MENU_LEVEL, MENU_STATUS, MENU_TYPE, MENU_ICON, MENU_URL, MENU_DESC) values ('MENU_020_010', 'MENU_020_010_030', '删除项目', '删除项目', 3, 0, 2, '', '/zhiqimProject/projectDelete.htm', '');
insert into ZMR_MENU (PARENT_CODE, MENU_CODE, MENU_NAME, MENU_NAME_ABBR, MENU_LEVEL, MENU_STATUS, MENU_TYPE, MENU_ICON, MENU_URL, MENU_DESC) values ('MENU_020_010', 'MENU_020_010_040', '转让项目', '转让项目', 3, 0, 2, '', '/zhiqimProject/projectTransferSelector.htm', '');
insert into ZMR_MENU (PARENT_CODE, MENU_CODE, MENU_NAME, MENU_NAME_ABBR, MENU_LEVEL, MENU_STATUS, MENU_TYPE, MENU_ICON, MENU_URL, MENU_DESC) values ('MENU_020', 'MENU_020_030', '项目代码仓库', '代码仓库', 2, 0, 1, 'z-briefcase', '/gitsolo/repository.htm', '');
commit;

-- ---------------------------------------------------
-- 知启蒙代码仓库独立版功能菜单创建完成
-- ---------------------------------------------------
