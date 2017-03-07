##Mybatis映射文件
####select元素返回类型resultType,resultMap
```xml
<select id="selectGoodById" parameterType="int" resultType="goods">
		select id,cate_id,name,price,description,order_no,update_time 
		from goods where id = #{id}
</select>
	
<select id="selectAllGoods" resultMap="t_good">
		select id,cate_id,name,price,description,order_no,update_time from goods
</select>
```
如上面映射配置所示,第一句采用resultType作为该参数返回类型的定义,这样查询该语句之后,数据库表中的字段就会映射到goods中各种属性.而该属性的值一般是权限定类名
resultMap属性,表示外部 resultMap 的命名引用.也就是ResultMap元素的引用,**其值为ResultMap元素中的id**
**以上两种属性不能同时出现**
***两者之间的区别:***

如果pojo的属性可以与数据库表中的属性对应起来,则直接使用resultType就可以,如果不能直接对应,要进行相关的映射处理,这时候选择resultMap.
----------


####sql元素
sql元素是用来定义可重用的SQL代码片段.定义完成之后,可以在其他sql语句内使用.
例如:
```xml
<sql id="userColumns"> ${alias}.id,${alias}.username,${alias}.password </sql>
```
如上述所示定义sql元素相关语句,下面是在select语句中使用该sql:
```xml
<select id="selectUsers" resultType="map">
  select
    <include refid="userColumns"><property name="alias" value="t1"/></include>
  from some_table t1
</select>
```
上面include元素是引用sql标签的元素,***property标签内的属性名称对应于sql标签中的alias变量,并且将改占位符的值设置为t1***,所以上面这句select 语句可以翻译成如下:
```sql
select
    t1.id,t1.username,t1.password
  from some_table t1
```


----------


####参数问题(parameterType)
```xml
<select id="selectPerson" parameterType="int" resultType="hashmap">
	SELECT * FROM PERSON WHERE ID = #{id}
</select>
```
上面指明了调用select语句时候,需要传入的参数类型为int类型,查询结果集返回的是hashMap.该语句首先会变成如下java语句:
```java
String selectUser = "select * from person where Id = ?"
PrepareStatement ps = conn.prepareStatement(selectUser);
ps.setInt(1,id);
```
``#{id}``先会告诉Mybatis先创建预处理语句参数.
除了上面简单的参数类型之外,还可以接收复杂的参数类型(接收一个对象参数)
例如
```xml
<select id="insertPerson" parameterType="user">
	insert into person values(#{id},#{name},#{password})
</select>
```
`#{id}`会告诉mybatis去寻找user对象中的id属性值,然后填充相关的sql语句.


----------


####ResultMap
#####基本映射关系
```xml
<resultMap id="blogResult" type="Blog">
  <id property="id" column="blog_id" />
  <result property="title" column="blog_title"/>
</resultMap>
```
- type表示实际的**返回类型**
- resultMap中两个子元素id和result.  **Id表示数据库主键,column表示表的字段,property表示数据映射** **到返回类型的属性**.
- ResultMap元素所起到的作用就是将***数据库表中的字段映射到java POJO中的各种字段***,如上述代码所示,Blog类中的id属性对应于数据库表中的blog_id属性(逐渐使用id子元素),title属性对应于数据库表中的blog_title属性(result元素)
- ResultMap存在是为了避免数据库字段与pojo字段对应不一致的情况,在编程的时候最好显示表明.
#####关联
```xml
<association property="author" column="blog_author_id" javaType="Author">
  <id property="id" column="author_id"/>
  <result property="username" column="author_username"/>
</association>
```
1.关联元素处理“有一个”类型的关系。比如,在我们的示例中,一	个博客有一个用户(来处理组合类)。
2.你需要告诉 MyBatis 如何加载关联。MyBatis 在这方面会有两种不同的 方式:
- **嵌套查询**:通过***执行另外一个 SQL 映射语句来返回预期的复杂类型***。
- **嵌套结果**:使用嵌套结果映射来处理重复的联合结果的子集

######关联的嵌套查询
|属性|描述|
|--|--|
|``column``|来自数据库的表格的列名,或重命名的列标签。|
|``select``|另外一个映射语句的 ID,可以加载这个属性映射需要的复杂类型。在获取的时候,**指定的列的值将被传递给目标 select 语句作为参数**。|
|``fetchType``|可选的。有效值为 lazy和eager|

```xml
<resultMap id="blogResult" type="Blog">
  <association property="author" column="author_id" javaType="Author" select="selectAuthor"/>
</resultMap>

<select id="selectBlog" resultMap="blogResult">
  SELECT * FROM BLOG WHERE ID = #{id}
</select>

<select id="selectAuthor" resultType="Author">
  SELECT * FROM AUTHOR WHERE ID = #{id}
</select>
```
在上面示例配制文件中,assocation元素中的select属性调用另一个sql查询,查询的结果填充author属性,而查询参数id就是数据库表中的author_id.

######关联的嵌套结果
|属性|描述|
|--|--|
|`resultMap`|指明另一个resutlMap的ID,可以映射关联的嵌套结果到一个合适的对象中|
|`columnPrefix`|当连接多表时，你将不得不使用列别名来避免ResultSet中的重复列名。指定columnPrefix允许你映射列名到一个外部的结果集中|
|`notNullColumn`|默认情况下，子对象仅在至少一个列映射到其属性非空时才创建。|
|`autoMapping`|如果使用了，当映射结果到当前属性时，Mybatis将启用或者禁用自动映射。|
示例代码:
```xml
<select id="selectBlog" resultMap="blogResult">
  select
    B.id            as blog_id,
    B.title         as blog_title,
    B.author_id     as blog_author_id,
    A.id            as author_id,
    A.username      as author_username,
    A.password      as author_password,
    A.email         as author_email,
    A.bio           as author_bio
  from Blog B left outer join Author A on B.author_id = A.id
  where B.id = #{id}
</select>
```
映射结果:
```xml
<resultMap id="blogResult" type="Blog">
  <id property="id" column="blog_id" />
  <result property="title" column="blog_title"/>
  <!--与上面字段对应
    B.id            as blog_id,
    B.title         as blog_title,
    B.author_id     as blog_author_id,
-->
  <association property="author" column="blog_author_id" javaType="Author" resultMap="authorResult"/><!--上面查询语句中Author表中的字段被映射成authorResult中的各种字段,两个表格的关联查询-->
  <!--与下面字段对应
	A.id            as author_id,
    A.username      as author_username,
    A.password      as author_password,
    A.email         as author_email,
    A.bio           as author_bio
-->
</resultMap>

<resultMap id="authorResult" type="Author">
  <id property="id" column="author_id"/>
  <result property="username" column="author_username"/>
  <result property="password" column="author_password"/>
  <result property="email" column="author_email"/>
  <result property="bio" column="author_bio"/>
</resultMap>
```
如果blog有一个co-author怎么办？ select语句将看起来这个样子：
```xml
<select id="selectBlog" resultMap="blogResult">
  select
    B.id            as blog_id,
    B.title         as blog_title,
    A.id            as author_id,
    A.username      as author_username,
    A.password      as author_password,
    A.email         as author_email,
    A.bio           as author_bio,
    CA.id           as co_author_id,
    CA.username     as co_author_username,
    CA.password     as co_author_password,
    CA.email        as co_author_email,
    CA.bio          as co_author_bio
  from Blog B
  left outer join Author A on B.author_id = A.id
  left outer join Author CA on B.co_author_id = CA.id
  where B.id = #{id}
</select>
```
再次调用Author的resultMap将定义如下：
```xml
<resultMap id="authorResult" type="Author">
  <id property="id" column="author_id"/>
  <result property="username" column="author_username"/>
  <result property="password" column="author_password"/>
  <result property="email" column="author_email"/>
  <result property="bio" column="author_bio"/>
</resultMap>
```
因为结果中的列名与resultMap中的列名不同。 你需要指定columnPrefix重用映射co-author结果的resultMap。
```xml
<resultMap id="blogResult" type="Blog">
  <id property="id" column="blog_id" />
  <result property="title" column="blog_title"/>
  <association property="author"
    resultMap="authorResult" />
  <association property="coAuthor"
    resultMap="authorResult"
    columnPrefix="co_" /><!--表示co_开头的数据表列名-->
</resultMap>
```

#####集合
```xml
<collection property="posts" ofType="domain.blog.Post">
  <id property="id" column="post_id"/>
  <result property="subject" column="post_subject"/>
  <result property="body" column="post_body"/>
</collection>
```
集合元素的作用几乎和关联是相同的。实际上,它们也很相似,文档的异同是多余的。 所以我们更多关注于它们的不同。我们来继续上面的示例,一个博客只有一个作者。但是博客有很多文章。在博客类中, 这可以由下面这样的写法来表示:
```java
private List<Post> posts;
```
要映射嵌套结果集合到 List 中,我们使用集合元素。就像关联元素一样,我们可以从 连接中使用嵌套查询,或者嵌套结果。
- **集合的嵌套查询**
首先,让我们看看使用嵌套查询来为博客加载文章。
```xml
<resultMap id="blogResult" type="Blog">
  <collection property="posts" javaType="ArrayList" column="id" ofType="Post" select="selectPostsForBlog"/>
</resultMap>

<select id="selectBlog" resultMap="blogResult">
  SELECT * FROM BLOG WHERE ID = #{id}
</select>

<select id="selectPostsForBlog" resultType="Blog">
  SELECT * FROM POST WHERE BLOG_ID = #{id}
</select>
```
这里你应该注意很多东西,但大部分代码和上面的关联元素是非常相似的。首先,你应该注意我们使用的是集合元素。然后要注意那个新的“ofType”属性。这个属性用来区分 ***JavaBean(或字段)属性和集合属性***。除了上面所知道的相关的属性之外,还存在`fetchType`属性上面,该属性存在两个值`eager`和`lazy`,前者表示调用的在访问blog对象的时候,会立刻发送sql语句得到相关posts属性值,后者表示只有在需要的时候才会发送sql语句得到相关的数据.`classId`属性是Blog对应表中的id字段.所以你可以读出下面这个 映射:
```xml
<collection property="posts" javaType="ArrayList" column="id" ofType="Post" select="selectPostsForBlog"/>
```
`读作: “在 Post 类型的 ArrayList 中的 posts 的集合。”`
javaType 属性是不需要的,因为 MyBatis 在很多情况下会为你算出来。所以你可以缩短 写法:
```xml
<collection property="posts" column="id" ofType="Post" select="selectPostsForBlog"/>
```
- **集合的嵌套结果**
至此,你可以猜测集合的嵌套结果是如何来工作的,因为它和关联完全相同,除了它应 用了一个“ofType”属性
```xml
<select id="selectBlog" resultMap="blogResult">
  select
  B.id as blog_id,
  B.title as blog_title,
  B.author_id as blog_author_id,
  P.id as post_id,
  P.subject as post_subject,
  P.body as post_body,
  from Blog B
  left outer join Post P on B.id = P.blog_id
  where B.id = #{id}
</select>
```
我们又一次联合了博客表和文章表,而且关注于保证特性,结果列标签的简单映射。现 在用文章映射集合映射博客,可以简单写为:
```xml
<resultMap id="blogResult" type="Blog">
  <id property="id" column="blog_id" />
  <result property="title" column="blog_title"/>
  <collection property="posts" ofType="Post">
    <id property="id" column="post_id"/>
    <result property="subject" column="post_subject"/>
    <result property="body" column="post_body"/>
  </collection>
</resultMap>
```


