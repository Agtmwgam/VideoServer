-- 告警信息表
create table warningMessage(
	warnId int PRIMARY KEY AUTO_INCREMENT,
	frame varchar(64) DEFAULT NULL,
	mesNo varchar(64) DEFAULT NULL,
	mesDate varchar(11) DEFAULT NULL,
	groupName varchar(64) DEFAULT NULL,
	deviceName varchar(64) DEFAULT NULL,
	deviceModel varchar(64) DEFAULT NULL,
	serial varchar(64) DEFAULT NULL,
	videoResolution varchar(64) DEFAULT NULL,
	targetLocation varchar(64) DEFAULT NULL,
	exeStatus varchar(11) DEFAULT NULL,
	ip varchar(11) DEFAULT NULL,
	videoPath varchar(128) DEFAULT NULL,
	isValid enum('0','1') DEFAULT '1',
	createTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP
	 );

-- 心跳信息表
create table beatMessage(
	beatId int PRIMARY KEY AUTO_INCREMENT,
	frame varchar(64) DEFAULT NULL,
	mesNo varchar(64) DEFAULT NULL,
	mesDate TIMESTAMP DEFAULT NULL,
	groupName varchar(64) DEFAULT NULL,
	deviceName varchar(64) DEFAULT NULL,
	deviceModel varchar(64) DEFAULT NULL,
	serial varchar(64) DEFAULT NULL,
	exeStatus varchar(11) DEFAULT NULL,
	ip varchar(11) DEFAULT NULL,
	isValid enum('0','1') DEFAULT '1',
	createTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP
	 );

-- 登陆报文表
create table loginMessage(
	loginId int PRIMARY KEY AUTO_INCREMENT,
	frame varchar(64) DEFAULT NULL,
	mesNo varchar(64) DEFAULT NULL,
	deviceVerifyCode varchar(64) DEFAULT NULL,
	deviceType varchar(64) DEFAULT NULL,
	softVersion varchar(64) DEFAULT NULL,
	productDate varchar(64) DEFAULT NULL,
	rand varchar(64) DEFAULT NULL,
	isValid enum('0','1') DEFAULT '1',
	createTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP
	 );

-- 设备关联设备组表
create table deviceGroupRelate(
	id int PRIMARY KEY AUTO_INCREMENT,
	deviceId int NOT NULL,
	groupId int NOT NULL,
	isValid enum('0','1') DEFAULT '1',
	createTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);