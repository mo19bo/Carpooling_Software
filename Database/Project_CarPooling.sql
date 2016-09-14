/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2008                    */
/* Created on:     2014/12/23 18:09:43                          */
/*==============================================================*/


if exists (select 1
            from  sysobjects
           where  id = object_id('Driver')
            and   type = 'U')
   drop table Driver
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('MyOrder')
            and   name  = 'driver_order_FK'
            and   indid > 0
            and   indid < 255)
   drop index MyOrder.driver_order_FK
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('MyOrder')
            and   name  = 'make_order_FK'
            and   indid > 0
            and   indid < 255)
   drop index MyOrder.make_order_FK
go

if exists (select 1
            from  sysobjects
           where  id = object_id('MyOrder')
            and   type = 'U')
   drop table MyOrder
go

if exists (select 1
            from  sysobjects
           where  id = object_id('"User"')
            and   type = 'U')
   drop table "User"
go

if exists (select 1
            from  sysobjects
           where  id = object_id('choose')
            and   type = 'U')
   drop table choose
go

if exists (select 1
            from  sysobjects
           where  id = object_id('comments')
            and   type = 'U')
   drop table comments
go

if exists(select 1 from systypes where name='IDcard')
   drop type IDcard
go

if exists(select 1 from systypes where name='Introduction')
   drop type Introduction
go

if exists(select 1 from systypes where name='License')
   drop type License
go

if exists(select 1 from systypes where name='Name1')
   drop type Name1
go

if exists(select 1 from systypes where name='PassWord')
   drop type PassWord
go

if exists(select 1 from systypes where name='PhoneNum')
   drop type PhoneNum
go

if exists(select 1 from systypes where name='Sex')
   drop type Sex
go

if exists(select 1 from systypes where name='as')
   drop type "as"
go

if exists(select 1 from systypes where name='asNum')
   drop type asNum
go

if exists(select 1 from systypes where name='brief')
   drop type brief
go

if exists(select 1 from systypes where name='com')
   drop type com
go

if exists(select 1 from systypes where name='date1')
   drop type date1
go

if exists(select 1 from systypes where name='id')
   drop type id
go

if exists(select 1 from systypes where name='is')
   drop type "is"
go

if exists(select 1 from systypes where name='job')
   drop type job
go

if exists(select 1 from systypes where name='location')
   drop type location
go

if exists(select 1 from systypes where name='map')
   drop type map
go

if exists(select 1 from systypes where name='price')
   execute sp_unbindrule price
go

if exists(select 1 from systypes where name='price')
   drop type price
go

if exists(select 1 from systypes where name='starttime')
   drop type starttime
go

if exists(select 1 from systypes where name='week')
   drop type week
go

if exists (select 1
   from  sysobjects where type = 'D'
   and name = 'D_0'
   )
   drop default D_0
go

if exists (select 1
   from  sysobjects where type = 'D'
   and name = 'D_1'
   )
   drop default D_1
go

if exists (select 1 from sysobjects where id=object_id('R_price') and type='R')
   drop rule  R_price
go

create rule R_price as
      @column between 0000000.00 and 9999999.99
go

/*==============================================================*/
/* Default: D_0                                                 */
/*==============================================================*/
create default D_0
    as 0
go

/*==============================================================*/
/* Default: D_1                                                 */
/*==============================================================*/
create default D_1
    as 1
go

/*==============================================================*/
/* Domain: IDcard                                               */
/*==============================================================*/
create type IDcard
   from varchar(20)
go

/*==============================================================*/
/* Domain: Introduction                                         */
/*==============================================================*/
create type Introduction
   from varchar(100)
go

/*==============================================================*/
/* Domain: License                                              */
/*==============================================================*/
create type License
   from varchar(20)
go

/*==============================================================*/
/* Domain: Name1                                                */
/*==============================================================*/
create type Name1
   from varchar(20)
go

/*==============================================================*/
/* Domain: PassWord                                             */
/*==============================================================*/
create type PassWord
   from varchar(20)
go

/*==============================================================*/
/* Domain: PhoneNum                                             */
/*==============================================================*/
create type PhoneNum
   from varchar(20)
go

/*==============================================================*/
/* Domain: Sex                                                  */
/*==============================================================*/
create type Sex
   from int
go

/*==============================================================*/
/* Domain: "as"                                                 */
/*==============================================================*/
create type "as"
   from float
go

execute sp_bindefault D_0, 'as'
go

/*==============================================================*/
/* Domain: asNum                                                */
/*==============================================================*/
create type asNum
   from int
go

execute sp_bindefault D_0, 'asNum'
go

/*==============================================================*/
/* Domain: brief                                                */
/*==============================================================*/
create type brief
   from varchar(30)
go

/*==============================================================*/
/* Domain: com                                                  */
/*==============================================================*/
create type com
   from float(10)
go

/*==============================================================*/
/* Domain: date1                                                */
/*==============================================================*/
create type date1
   from datetime
go

/*==============================================================*/
/* Domain: id                                                   */
/*==============================================================*/
create type id
   from varchar(15)
go

/*==============================================================*/
/* Domain: "is"                                                 */
/*==============================================================*/
create type "is"
   from bit
go

execute sp_bindefault D_0, 'is'
go

/*==============================================================*/
/* Domain: job                                                  */
/*==============================================================*/
create type job
   from varchar(20)
go

/*==============================================================*/
/* Domain: location                                             */
/*==============================================================*/
create type location
   from varchar(30)
go

/*==============================================================*/
/* Domain: map                                                  */
/*==============================================================*/
create type map
   from varchar(100)
go

/*==============================================================*/
/* Domain: price                                                */
/*==============================================================*/
create type price
   from money
go

execute sp_bindrule R_price, price
go

/*==============================================================*/
/* Domain: starttime                                            */
/*==============================================================*/
create type starttime
   from varchar(20)
go

/*==============================================================*/
/* Domain: week                                                 */
/*==============================================================*/
create type week
   from bit
go

execute sp_bindefault D_1, 'week'
go

/*==============================================================*/
/* Table: Driver                                                */
/*==============================================================*/
create table Driver (
   driverid             id                   not null,
   server_num           smallint             not null,
   start_time           starttime            null,
   week1                week                 not null,
   week2                week                 not null,
   week3                week                 not null,
   week4                week                 not null,
   week5                week                 not null,
   week6                week                 not null,
   week7                week                 not null,
   publish_data         date1                not null,
   end_data             date1                not null,
   thetotalprice        price                not null,
   brief                brief                not null,
   location             location             not null,
   map_begin            map                  not null,
   map_end              map                  not null,
   isgo                 "is"                 not null,
   drivername           Name1                not null,
   constraint PK_DRIVER primary key nonclustered (driverid)
)
go

/*==============================================================*/
/* Table: MyOrder                                               */
/*==============================================================*/
create table MyOrder (
   orderid              id                   not null,
   userid               id                   not null,
   driverid             id                   not null,
   israte               "is"                 not null,
   isarr                "is"                 not null,
   constraint PK_MYORDER primary key nonclustered (orderid)
)
go

/*==============================================================*/
/* Index: make_order_FK                                         */
/*==============================================================*/
create index make_order_FK on MyOrder (
userid ASC
)
go

/*==============================================================*/
/* Index: driver_order_FK                                       */
/*==============================================================*/
create index driver_order_FK on MyOrder (
driverid ASC
)
go

/*==============================================================*/
/* Table: "User"                                                */
/*==============================================================*/
create table "User" (
   userid               id                   not null,
   username             Name1                not null,
   password             PassWord             not null,
   phonenum             PhoneNum             not null,
   sex                  Sex                  not null,
   introduction         Introduction         not null,
   driverlicense        License              null,
   idcard               IDcard               null,
   isdriver             "is"                 not null,
   iscustomer           "is"                 not null,
   asdriverrate         "as"                 not null,
   asdrnum              asNum                not null,
   aspassenger          "as"                 not null,
   aspnum               asNum                not null,
   job                  job                  not null,
   constraint PK_USER primary key nonclustered (userid)
)
go

/*==============================================================*/
/* Table: choose                                                */
/*==============================================================*/
create table choose (
   driverid             id                   not null,
   userid               id                   not null,
   constraint PK_CHOOSE primary key nonclustered (driverid, userid)
)
go

/*==============================================================*/
/* Table: comments                                              */
/*==============================================================*/
create table comments (
   userid               id                   not null,
   com_drive            com                  null,
   com_pick             com                  null,
   constraint PK_COMMENTS primary key nonclustered (userid)
)
go

