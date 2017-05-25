### bigdataLIb通用库
#### 1.介绍
###### bigdataLib通用库是一款快速开发androidAPP打造的通用库，这款库包含四大项目：网络请求库，图片加载库，常用工具库，UI常用库，现在处于第一版，UI常用库没有很完善。
#### 2.lib目录介绍
![](https://github.com/xkun1/BigDataLib/blob/master/mylibrary/src/main/java/image/image01.png)
#### 3.lib使用说明
	1.将lib加入工程中
![](https://github.com/xkun1/BigDataLib/blob/master/mylibrary/src/main/java/image/image02.png)
	
	2.在你的Application中调用
	Library.INSTANCE.init(this);
	Library.INSTANCE.initBaseUrl("https://bbcj.chinabigdata.com/app/");
	
	init-》初始化类库
	initBaseUrl-》为网络库添加域名地址
	
	3.继承LibraryBaseActivity 实现init方法
	在init方法中创建你ApiServer
	
	open class BaseActivity : LibraryBaseActivity() {

    var mApiServer: ApiServer? = null

    override fun init() {
        mApiServer = mRetroFactory!!.create(ApiServer::class.java)
    }

	}
	4.直接可以使用了
