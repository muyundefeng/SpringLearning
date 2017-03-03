### 数据库设计文档
blog_table
| 字段| 类型 | 是否为空 | 是否为主键 | 说明 |
|--|--|--|--|--|
| blog_Id | varchar(255)  | No | Yes | 标识博客字段 |
| blog_title | varchar(255)  | No | No | 博客标题 |
| blog_release_time | Date  | No | No | 发表时间 |
| blog_hits | int | Yes | No | 阅读量 |
| blog_support | int |Yes | No | 点赞量 |
| blog_comment_num | int | Yes | No | 评论量 |
| blog_content | longtext | No | No |博客内容 |
| blog_cateId | int | No | No | 所属类别 |
| blog_wordCount | bigint | No | No | 字数 |
| blog_isOrigina | int | No | No | 是否原创 |
| blog_cite_origina | varchar(255) | Yes | No |引用处 |

**说明：**blog_Id字段是根据blog_title以及blog_release_time生成的
blog_comment
|字段|类型|是否为空|是否为主键|说明|
|--|--|--|--|--|
|comment_id|varchar(255)|No|Yes|标识评论|
|blog_id|varchar(255)|No|No|博客标识|
|comment_content|varchar(255)|No|No|评论内容|
|commnet_user|varchar(255)|No|No|评论者|
|comment_time|varchar(255)|No|No|评论时间|
**说明：**comment_id是根据comment_time+comment_user生成的

### 创建数据表
创建blog_table表：
``` sql
drop table if exists blog_table;
CREATE TABLE `blog_table` (
`blog_Id` varchar(255) not null  PRIMARY KEY,
`blog_title` varchar(255) not null,  
`blog_release_time` Date not null,  
`blog_hits` int ,
`blog_support` int,
`blog_comment_num` int,
`blog_content` longtext NOT NULL,
`blog_cateId` int not null,
`blog_wordCount` int not null,
`blog_isOrigina` int not null,
`blog_cite_origina` varchar(255)) 
ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
```
创建blog_comment表：
```sql
drop table if exists blog_comment;
CREATE TABLE `blog_comment` (
`comment_id` varchar(255) not null  PRIMARY KEY,
`blog_id` varchar(255) not null,  
`comment_content` longtext not null,  
`commnet_user` varchar(255) not null ,
`comment_time` Date not null) 
ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;  
```
### 实现的基本功能
1.分页得到博文的列表
2.展示博文内容以及用户评论
3.阅读者可以发表评论
4.添加，删除博文

### 接口的定义
1.添加博文,**请求方式:POST**
请求url地址：```http://host:port/insertBlog```
请求实体：
``` json
{
	"blog_title":"",
	"blog_content":"",
	"blog_cateId":"",
	"blog_isOrigina":1,//1代表原创，0代表引用
	"blog_cite_origina":""
}
```
**note:**类别信息总结如下
|类别|请求参数值
|--|--|
|全部|0|
|编程语言|1|
|服务器开发|2|
|自然语言处理|3|
|大数据|4|
|移动开发|5|
2.删除博文，**请求方式：GET**
请求url地址：```http://host:port/deleteBlog?blogId={blogId}```
3.获得博文列表，**请求方式：GET**
请求地址：```http://host:port/getBlogs?cateId={cateId}```

请求返回格式:
```json
{
  "msg": "success",
  "list": [
    {
      "commentNum": 0,
      "cate": "大数据",
      "title": "Android设计模式学习之Builder模式"
    },
    {
      "commentNum": 0,
      "cate": "大数据",
      "title": "Android设计模式学习之Builder模式"
    },
    {
      "commentNum": 0,
      "cate": "大数据",
      "title": "Android设计模式学习之Builder模式"
    },
    {
      "commentNum": 0,
      "cate": "大数据",
      "title": "Android设计模式学习之Builder模式"
    },
    {
      "commentNum": 0,
      "cate": "大数据",
      "title": "Android设计模式学习之Builder模式"
    },
    {
      "commentNum": 0,
      "cate": "大数据",
      "title": "Android设计模式学习之Builder模式"
    },
    {
      "commentNum": 0,
      "cate": "大数据",
      "title": "Android设计模式学习之Builder模式"
    },
    {
      "commentNum": 0,
      "cate": "大数据",
      "title": "Android设计模式学习之Builder模式"
    }
  ]
}
```
4.获得单个博文信息，**请求方式：GET**
请求地址：```http://host:port/getSingleBlog?blogId={blogId}```

请求返回格式:
```json
{
  "blogId": "5e722dfe2f2671d18de2de9b386bbbb65173457759e136b269a42c5015dd7d43",
  "blogTitle": "Android设计模式学习之Builder模式",
  "blogReleaseTime": 1488470400000,
  "blogHits": 0,
  "blogSupport": 0,
  "blogCommentNum": 0,
  "blogCateid": 5,
  "blogWordcount": 127,
  "blogIsorigina": 0,
  "blogCiteOrigina": "http://blog.csdn.net/u012124438/article/details/59777619",
  "blogContent": "建造者模式（Builder Pattern），是创造性模式之一，Builder 模式的目的则是为了将对象的构建与展示分离。Builder 模式是一步一步创建一个复杂对象的创建型模式，它允许用户在不知道内部构建细节的情况下，可以更精细地控制对象的构造流程。"
}
```
5.获得用户的相关评论，**请求方式：GET**
请求地址：```http://host:port/getComment?pageIndex={pageIndex}&pageCount={pageCount}&blogId={blogId}```

请求返回格式:
```json
{
  "msg": "success",
  "total": 6,
  "details": [
    {
      "commnetUser": "asdada",
      "commentId": "cb422ed773cba47677962c90349935c58585e80b21a829ac0e9f5239ff43947a",
      "commentContent": "asda",
      "commentTime": "Fri Mar 03 00:00:00 CST 2017",
      "blogId": "510d98a519f7473947d7dd328cbf0374d72d0a785251fee5ebbc8e0141323fd2"
    },
    {
      "commnetUser": "asdada",
      "commentId": "ccf5af8cfd7e6658c937de8bee94659e0ef06990408d4b919a7d5fa7b44ef7a9",
      "commentContent": "asda",
      "commentTime": "Fri Mar 03 00:00:00 CST 2017",
      "blogId": "510d98a519f7473947d7dd328cbf0374d72d0a785251fee5ebbc8e0141323fd2"
    }
  ],
  "status": 1
}
```
6.添加用户相关评论，**请求方式：POST**
请求地址：```http://host:port/addComment```
请求**Entity：**
```json
{
	"blog_id":"",
	"comment_content":"",
	"commnet_user":""
}
```
请求返回json格式:
```json
{
  "blogId": "5e722dfe2f2671d18de2de9b386bbbb65173457759e136b269a42c5015dd7d43",
  "blogTitle": "Android设计模式学习之Builder模式",
  "blogReleaseTime": 1488470400000,
  "blogHits": 0,
  "blogSupport": 0,
  "blogCommentNum": 0,
  "blogCateid": 5,
  "blogWordcount": 127,
  "blogIsorigina": 0,
  "blogCiteOrigina": "http://blog.csdn.net/u012124438/article/details/59777619",
  "blogContent": "建造者模式（Builder Pattern），是创造性模式之一，Builder 模式的目的则是为了将对象的构建与展示分离。Builder 模式是一步一步创建一个复杂对象的创建型模式，它允许用户在不知道内部构建细节的情况下，可以更精细地控制对象的构造流程。"
}
```
