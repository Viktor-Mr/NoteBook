## ACL权限控制

​	<font color='red'>**ACL是基于具体资源权限控制，英文全程Access Control List**</font>

​	ACL是最早也是最基本的一种访问控制机制，它的原理非常简单：每一项资源，都配有一个权限列表，这个列表记录的就是哪些用户可以对这项资源执行CRUD中的那些操作。

​	当用户访问某资源时，会先检查这个列表中是否有关于当前用户的访问权限（鉴权），从而确定当前用户可否执行相应的操作。总得来说，ACL是一种面向资源的访问控制模型，它的机制是围绕“资源”展开的。

**优点：**

​	<font color='red'>实现简单，方便项目集成。</font>

**缺点：**

​	需要维护大量的权限列表，在性能上有明显的缺陷。另外，对于拥有大量用户与众多资源的应用，管理访问控制列表本身就变成非常繁重的工作。

**应用场景：**

​		在分享资源的场景，某个资源分享给某些人可用，例如分享我们的解说视频给群里的小伙伴。


## ACL的构成

​	 通俗一点讲，acl就是规则，每个对象都有若干条规则（这里说的对象指的是公共库，文件夹，文档，文档版本，数据集，功能类型），每条规则对应一个权限访问种类(`accessorType`)，如World，Owning_User，Owning_Group，Group，Role，User，System_Administrator，而每条规则有对应的授权权限列表`grantedNames`和拒绝权限列表`revokedNames`，权限包括READ，WRITE，DELETE，COPY，CHANGE，DOWNLOAD......



<font color='red'>**ACL实体** </font>

| 字段           | 类型        | 备注                                                         |
| :------------- | :---------- | :----------------------------------------------------------- |
| `objectType`   | 枚举类型    | 对象类型，如DOC，DATASET，功能权限的话是FUNCTION             |
| `objectId`     | String      | 如果是对象权限，就存放对象的数据库id， 如果是功能权限，就存放 “function” |
| `accessorType` | String      | 策略类型，定义使用哪种策略来限定用户范围，Group Policy, Owning Group Policy, Owning User Policy, ... |
| `accessorId`   | String      | 授权对象的Id，当授权对象为人时，则为userId                   |
| `grantedNames` | String List | 授权的权限，READ, WRITE                                      |
| `revokedNames` | String List | 拒绝的权限                                                   |
| `accessorName` | String      | 授权对象名称 该属性是ACL维护完后，返回到前端的显示值         |
| `isNeedSync`   | Boolean     | 是否同步到TC的标志                                           |
| `uid`          | String      | 如果isNeedSync为true，这个就是acl所绑定的对象uid             |



**<font color='red'>Object DTO</font>**

| 字段            | 类型    | 备注                                  |
| :-------------- | :------ | :------------------------------------ |
| `id`            | String  | 对象ID，功能权限就写function          |
| `uid`           | String  | 当isNeedSync为true时候使用，对象的uid |
| `ownerId`       | String  | 对象的所有者                          |
| `ownerGroup`    | String  | 对象的所有组                          |
| `isHasStatus`   | boolean | 是否有发布状态                        |
| `releaseStatus` | String  | 发布状态                              |
| `isNeedSync`    | boolean | 是否同步TC                            |
| `typeName`      | String  | 对象类型名字                          |
| `className`     | String  | 对象在全局acl的xml文件里sheet类型名字 |



<font color='red'>**策略类型accessorType**</font>

| 类型                   | 是否需要指定accessorId | 备注                     |
| :--------------------- | :--------------------- | :----------------------- |
| `Group`                | 是                     | 对指定具体的组范围适用   |
| `Owning Group`         | 否                     | 对对象（功能）所有组适用 |
| `Owning User`          | 否                     | 对对象（功能）所有者适用 |
| `Role`                 | 是                     | 对指定具体的角色适用     |
| `System Administrator` | 否                     | 对系统管理员角色适用     |
| `User`                 | 是                     | 对指定的用户适用         |
| `World`                | 否                     | 对所有用户适用           |



<font color='red'>**ACL样例**</font>

每一行都是一个acl，acl可以一个规则，每个对象，都会有很多规则

| objectId | objectType | accessorId        | accessorType         | grantedNames                 | revokedNames |
| -------- | ---------- | ----------------- | -------------------- | ---------------------------- | ------------ |
| 101      | DOC        | null              | System_Administrator | 读，写，删，复制，修改，下载 |              |
| 101      | DOC        | KOMOO.JOMOO GROUP | Group                | 读，下载                     | 删           |
| 101      | DOC        | 00096063          | User                 | 读，写，复制，下载           | 删           |
| 101      | DOC        | CNC Designer      | role                 |                              |              |
|          |            |                   |                      |                              |              |

解释：场景分析-> <font color='red'>用户admin </font>对 <font color='red'>id为101文档对象</font>进行操作，请求发到后台进行鉴权（对当前登录用户鉴权）。

​	后台得到id为101的文档对象所有ACL规则（进行排序，避免冲突），判断那条规则适用于当前用户，返回<font color='red'>当前用户admin</font>的权限。





## 详解

​	emop-acl有两种类型的acl

* 一种是针对单个对象的acl

* 一种是全局acl，不针对具体对象，所以没有objectId ，可能被很多对象都满足，那么这么对象都可以使用这条acl 。

![image-20221106150521588](http://mk-images.tagao.top/img/image-20221106150521588.png?imageslim)**<font color='red'>Condition实体 存储全局ACL的实体</font>**

| 字段                     | 解释                | 图中颜色 |
| ------------------------ | ------------------- | -------- |
| id;                      | 条件ID              | 黑色     |
| parent                   | 上级条件ID          | 绿色     |
| condition                | 条件表达式          | 橙色     |
| List<Acl> aclList        | 条件所携带的ACL列表 | 红色     |
| List<Condition> children | 子条件              | 蓝色     |

通过读取execl的信息加载acl，每一个sheet也都是一种对象类型，**条件以树结构存放，先判断父级，在判断子级，子级的优先级比父级高**。



### 获取对象所有ACL

```Java
@RequestMapping(value = "/getAclList", method = RequestMethod.POST)
public RestResponse<List<AclDTO>> getAclList(@Valid @RequestBody ObjectDTO objectDTO)
```

> ACL 是有顺序的，先找定制化权限，再找全局权限, 如果是流程中，则将流程中的ACL放到最前面

1. 首先会去mongodb中根据objectId查找对象的所有EmopAcl，然后将List<EmopAcl>转化为List<Acl>返回，此Acl权限列表属于对象权限列表，也就是定制化权限列表

2. 然后会去excel中获取全局权限列表和流程中权限列表，此处会根据objectId先查找className，此className为excel中sheet的名字，然后判断global_acl.xlsx有没有此sheet，如果有，则把此sheet中的所有Condition都放入List<Condition> aclConditionTrees。之后判断此objectDTO是否在流程中，如果在，则把Workflow这个sheet的所有Condition都放入List<Condition> aclConditionTrees。

   > excel中的每个sheet加载到内存中后，是一个List<Condition>，Condition有父子结构，每个Condition都有一个Acl列表，可以看作excel中每一小行都是一个acl

3. aclConditionTrees中不是所有的acl都适配此ObjectDTO，所以需要过滤出适配的acl。如何过滤？遍历aclConditionTrees，对每个aclCondition，获取aclCondition的条件/值，根据条件获得条件解析器（条件解析器：HasClassConditionResolver, HasStatusConditionResolver, IsInProcessConditionResolver等等），若获取的条件解析器为空，则直接跳过此aclCondition。然后再判断此条件是否匹配过，若匹配过，也跳过此aclCondition，如果是类型匹配判断，当存在任何一个匹配后缀为Revision的类型时，则不继续判断； 因为其父类 ItemRevision在最后一定会满足条件要求（避免重复的全局ACL）。最后根据条件解析器执行intercept方法（传入objectDTO，条件值conditionParam）判断是否适配此aclCondition。如果适配，则获取此aclCondition下的所有acl

4. 继续执行子条件匹配，合并全局和定制化ACL权限， 因key相同 所以map需要合并，如果对象在流程中，则进行调整



### 校验一个对象的多个权限

```Java
@RequestMapping(value = "/check", method = RequestMethod.POST)
public RestResponse<Map<Permission, Boolean>> check(@RequestBody ObjectDTO objectDTO, @RequestParam Permission[] permissions)
```

1. 首先会获取ObjectDTO的acl列表，校验权限之前，会调整acl的顺序

   > 权限判断的 总体顺序是 流程 > 对象权限 > 全局权限
   >
   > 局部顺序是 管理员 > 所有者（组/用户） > 普通权限 > Word 所有人权限 

2. 遍历所有的acl，先判断此acl是否过时，若过时则跳过此acl，再根据此acl的权限访问类型获得策略解析器（WorldPolicyResolver，OwningUserPolicyResolver，OwningGroupPolicyResolver，GroupPolicyResolver，RolePolicyResolver，UserPolicyResolver，SystemAdministratorPolicyResolver）没有获取到该策略机制则跳过，然后在该策略解析器上判断此objectDTO与acl是否匹配，若匹配，则得到此acl的授权权限grantedNamesNotNull与拒绝权限列表revokedNamesNotNull。

3. 在此acl内遍历所有待判断的权限列表permissions，若已授权权限列表grantedNamesNotNull包含此待判断的权限permission，则此permission对应的布尔值为true，代表有权限，若拒绝权限列表revokedNamesNotNull包含此待判断的权限permission，则此对应的布尔值为false，代表无权限































a对象：公共库，文件夹，文档，文档版本，数据集，功能类型

a对象  3条权限规则  objectId   其中一条是针对用户规则的user(accessorId),    role,      group, 。。

account

当前用户 admin  验证对a对象是否有权限

判断当前用户对a对象有没有什么权限，

​    

| objectID | accessorType | accessorId        |
| -------- | ------------ | ----------------- |
| 111111   | USER         | 00001             |
| 111111   | GROUP        | KOMOO.JOMOO GROUP |
|          |              |                   |
| 111111   | ROLE         | S24124214124      |

  

针对a设置了哪些权限





acl指的是规则，每个对象（文档，数据集）都有一条至多条规则，每条规则的权限类型不一样（针对的对象不一样）如针对user，role，group，owningGroup等等，excel文件里面的是全局acl，不单单属于某个对象（文档，数据集），所以excel里面的每条acl规则的objectId都为null。mongodb里面的emop_acl是对象acl，每条acl规则都属于某个具体的对象（文档，数据集等），所以objectId必然不为空



那excel中这么多acl规则，到底哪些acl规则才是属于某个对象（文档，数据集）的呢，换句话说，给定一个对象（文档，数据集），如何从excel中找到属于这个对象的acl规则呢，首先对象（ObjectDTO）都包含一个className，此className即为excel的sheetName，所以先锁定此对象在哪个sheet，然后去此sheet查找，excel中所有主键相同的行条件值都相同，可以看作，每行都包含一个acl规则，每条acl规则都有自己的条件，首先会根据此条件的key找到对应的条件解析器，然后调用条件解析器的intercept方法判断此对象是否满足条件（此方法会传入ObjectDTO和条件值两个参数）如果满足，则说明此acl规则属于此对象











​	
