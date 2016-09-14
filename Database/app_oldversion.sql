-- phpMyAdmin SQL Dump
-- version 3.3.8.1
-- http://www.phpmyadmin.net
--
-- 主机: w.rdc.sae.sina.com.cn:3307
-- 生成日期: 2014 年 12 月 02 日 18:11
-- 服务器版本: 5.5.23
-- PHP 版本: 5.3.3

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- 数据库: `app_miqilin`
--

-- --------------------------------------------------------

--
-- 表的结构 `driver`
--

CREATE TABLE IF NOT EXISTS `driver` (
  `pid` int(10) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `server_num` int(5) NOT NULL,
  `start_time` varchar(20) NOT NULL,
  `weeks1` int(11) NOT NULL,
  `weeks2` int(11) NOT NULL,
  `weeks3` int(11) NOT NULL,
  `weeks4` int(11) NOT NULL,
  `weeks5` int(11) NOT NULL,
  `weeks6` int(11) NOT NULL,
  `weeks7` int(11) NOT NULL,
  `publish_date` date NOT NULL,
  `end_date` date NOT NULL,
  `thetotalprice` double NOT NULL,
  `brief` varchar(30) NOT NULL,
  `location` varchar(30) NOT NULL,
  `map_begin` varchar(100) NOT NULL,
  `map_end` varchar(100) NOT NULL,
  `isgo` int(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`pid`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=12 ;

--
-- 转存表中的数据 `driver`
--

INSERT INTO `driver` (`pid`, `username`, `server_num`, `start_time`, `weeks1`, `weeks2`, `weeks3`, `weeks4`, `weeks5`, `weeks6`, `weeks7`, `publish_date`, `end_date`, `thetotalprice`, `brief`, `location`, `map_begin`, `map_end`, `isgo`) VALUES
(8, 'testdriver1', 1, '17:00', 1, 1, 1, 0, 0, 0, 0, '2014-11-13', '2014-11-14', 15, 'hello', '', 'Tianfu Square', 'Qingyang Tample', 0),
(2, 'testdriver2', 4, '11:00', 0, 1, 0, 0, 0, 0, 0, '2014-11-14', '2014-11-15', 50.3, 'hello world ', '', 'Chengdu North Realway Station', 'Kuanzhai Xiangzi', 0);

-- --------------------------------------------------------

--
-- 表的结构 `myorder`
--

CREATE TABLE IF NOT EXISTS `myorder` (
  `opid` int(10) NOT NULL AUTO_INCREMENT,
  `pid` int(10) NOT NULL,
  `pusername` varchar(20) NOT NULL,
  `isarr` int(11) NOT NULL DEFAULT '0',
  `ispgo` int(11) NOT NULL DEFAULT '0',
  `israte` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`opid`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=8 ;

--
-- 转存表中的数据 `myorder`
--

INSERT INTO `myorder` (`opid`, `pid`, `pusername`, `isarr`, `ispgo`, `israte`) VALUES
(1, 1, 'testpassenger1', 0, 0, 0),
(2, 1, 'testpassenger2', 0, 0, 0),
(3, 2, 'testpassenger1', 0, 0, 0),
(5, 8, 'testpassenger1', 1, 1, 0),
(6, 8, 'testpassenger2', 0, 1, 0),
(7, 8, 'testpassenger3', 1, 1, 0);

-- --------------------------------------------------------

--
-- 表的结构 `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `username` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  `phonenumber` varchar(20) NOT NULL,
  `sex` int(5) NOT NULL,
  `introduction` varchar(100) NOT NULL,
  `driverlicense` varchar(20) DEFAULT NULL,
  `idcard` varchar(20) DEFAULT NULL,
  `isdriver` int(5) NOT NULL,
  `iscustomer` int(5) NOT NULL,
  `asdriverrate` float NOT NULL DEFAULT '0',
  `asdriverratenum` int(10) NOT NULL DEFAULT '0',
  `aspassenger` float NOT NULL DEFAULT '0',
  `aspassengernum` int(10) NOT NULL DEFAULT '0',
  `job` varchar(20) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `user`
--

INSERT INTO `user` (`username`, `password`, `phonenumber`, `sex`, `introduction`, `driverlicense`, `idcard`, `isdriver`, `iscustomer`, `asdriverrate`, `asdriverratenum`, `aspassenger`, `aspassengernum`, `job`) VALUES
('testpassenger2', 'test', '18328588449', 0, 'hello word', '', '', 0, 1, 3, 1, 3, 10, 'worker'),
('testpassenger1', '123456', '18328588447', 1, '', '', '', 0, 1, 0, 0, 0, 0, 'student'),
('hercules', '123456', '18328588440', 1, 'I am a student', '', '', 1, 1, 3.3, 1, 4, 10, 'student'),
('testpassenger3', '123456', '18328588441', 0, 'hello word', NULL, NULL, 0, 1, 0, 0, 0, 0, 'worker'),
('testdriver1', '123456', '18328588442', 1, 'hello word', NULL, NULL, 1, 0, 5, 3, 0, 0, 'police'),
('testdriver2', '123456', '18328588440', 1, 'hello word', NULL, NULL, 1, 0, 0, 0, 0, 0, 'worker'),
('tony_pod', '123456', '12345678901', 0, 'asdfghjkl', '', '', 0, 1, 0, 0, 0, 0, ''),
('邓维佳', '1', '18782165597', 0, '', '', '', 0, 1, 0, 0, 0, 0, ''),
('hc', 'hc', '12345678911', 0, '', '', '', 0, 0, 0, 0, 0, 0, '');
