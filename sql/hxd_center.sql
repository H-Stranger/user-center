/*
 Navicat Premium Dump SQL

 Source Server         : mysql8.0
 Source Server Type    : MySQL
 Source Server Version : 80036 (8.0.36)
 Source Host           : localhost:3306
 Source Schema         : hxd_center

 Target Server Type    : MySQL
 Target Server Version : 80036 (8.0.36)
 File Encoding         : 65001

 Date: 19/03/2026 18:08:25
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `username` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `userAccount` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '账号',
  `avatarUrl` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户头像',
  `gender` tinyint NULL DEFAULT NULL COMMENT '性别',
  `userPassword` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `phone` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '电话',
  `email` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
  `userStatus` int NOT NULL DEFAULT 0 COMMENT '状态 0 - 正常',
  `createTime` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `isDelete` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除',
  `userRole` int NOT NULL DEFAULT 0 COMMENT '用户角色 0-普通用户  1-管理员',
  `planetCode` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '星球编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('', 1, '', '', 0, '', '', '', 0, '2026-03-16 14:30:07', '2026-03-17 20:04:47', 1, 0, NULL);
INSERT INTO `user` VALUES ('1', 2, '5446', '', 0, '875456', '', '', 0, '2026-03-16 14:42:52', '2026-03-16 14:42:52', 0, 0, NULL);
INSERT INTO `user` VALUES ('1', 3, '5446', '', 0, '875456', '', '', 0, '2026-03-16 14:43:48', '2026-03-16 14:43:48', 0, 0, NULL);
INSERT INTO `user` VALUES ('yupige', 4, 'yupi', '1', 1, '3214cdd850b7e5a51eb0095f3b1aafb7', '123456789', '35789456123@qq.com', 0, '2026-03-16 17:16:21', '2026-03-17 17:49:15', 0, 1, '1');
INSERT INTO `user` VALUES (NULL, 5, 'yupi1', NULL, NULL, '3214cdd850b7e5a51eb0095f3b1aafb7', NULL, NULL, 0, '2026-03-17 17:53:00', '2026-03-17 17:53:00', 0, 0, NULL);
INSERT INTO `user` VALUES (NULL, 6, 'zhangsan123', NULL, NULL, '3214cdd850b7e5a51eb0095f3b1aafb7', NULL, NULL, 0, '2026-03-17 19:19:11', '2026-03-17 19:19:11', 0, 0, '1001');
INSERT INTO `user` VALUES (NULL, 7, 'zhangsan1234', NULL, NULL, '3214cdd850b7e5a51eb0095f3b1aafb7', NULL, NULL, 0, '2026-03-18 16:51:16', '2026-03-18 16:51:16', 0, 0, '1000');
INSERT INTO `user` VALUES (NULL, 8, 'zhangsan12345', NULL, NULL, '3214cdd850b7e5a51eb0095f3b1aafb7', NULL, NULL, 0, '2026-03-18 16:54:16', '2026-03-18 16:54:16', 0, 0, '1002');
INSERT INTO `user` VALUES (NULL, 9, 'zhangsan12344', NULL, NULL, '3214cdd850b7e5a51eb0095f3b1aafb7', NULL, NULL, 0, '2026-03-18 20:21:17', '2026-03-18 20:21:17', 0, 0, '100');

SET FOREIGN_KEY_CHECKS = 1;
