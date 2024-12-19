# AudiobookSystem
听书功能流程图
This content is only supported in a Feishu Docs

小说处理流程顺序图
This content is only supported in a Feishu Docs
 
1. 数据库连接如何实现
数据库采用的是mysql数据库，数据库连接使用的是Spring Data JPA 框架，通过JPA框架可以便捷的操作数据库，JPA框架底层实现是通过

刚刚开始学习的时候连接数据库使用JDBC，JDBC是一个接口规范，对应不同数据库如mysql,oracle,sql server有不同的实现，且数据库操作直接使用sql语句，开发起来和数据库交互性较差。
[Image]
我们选择的是Spring Data JPA框架，框架 采用ORM 规范，该规范并不是具体的实现，具体的实现由各个数据库厂商来完成，因此，通过JPA框架可以连接在任意类型的主流数据库上。ORM规范处理了元数据描述对象和表之间的映射关系，框架将实体对象持久化到数据库表中，因此通过该框架可以通过java对象操作数据库。

2. 文字如何转语音
文字转语音主要实现方式是通过搜狗文字转语音的接口，在调用接口前，由于接口对于文字长度限制，前端需要将小说内容切割成成500长度，然后调用搜狗文字转语音接口传入文本，搜狗平台会返回对于语音文件，前端只要将该语音文件进行播放就可以，完成文字转语音。

3. 书籍分类与id关系
[Image]
书籍的主键ID，是书籍分类表里book_id的外键，分类通过外键可以查询到所属的书籍信息。
书籍和分类的关系是一对多，书籍是1，分类是多

前后端分离，完整项目联系本人qq 2441051071

首页
![image](https://github.com/user-attachments/assets/3b8c07fa-5282-401e-91cd-57f46d89640d)

管理员登录实现
![image](https://github.com/user-attachments/assets/d6d9931b-1eb1-4025-8e20-d00c258db6ff)

管理员登录实现
![image](https://github.com/user-attachments/assets/cc6baa5e-dee0-4b1f-8e19-d563ce875712)

听书
![image](https://github.com/user-attachments/assets/f92fa8f8-c7a9-42bf-9be7-03462e315729)

书籍管理
![image](https://github.com/user-attachments/assets/e8fcb2c2-7ed5-4f26-a341-bd9806282f65)
