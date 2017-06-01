### bigdataLIb通用库
### 展示
![](https://github.com/xkun1/BigDataLib/blob/master/mylibrary/src/main/java/image/image05.png)
#### 1.介绍
###### 网络库，image图库，Log库，文件下载库，Toast库，UI库，和一些常用的工具，二维码生成和扫描，图表库，该项目中也简单使用了Kotlin，节省了好多代码挺好的
#### 2.lib目录介绍
![](https://github.com/xkun1/BigDataLib/blob/master/mylibrary/src/main/java/image/image01.png)
#### 3.lib使用说明
	1.将lib加入工程中
![](https://github.com/xkun1/BigDataLib/blob/master/mylibrary/src/main/java/image/image02.png)
	
	2.在你的Application中调用
	Library.INSTANCE.init(this);
	Library.INSTANCE.initBaseUrl("https://bbcj.chinabigdata.com/app/");
	
![](https://github.com/xkun1/BigDataLib/blob/master/mylibrary/src/main/java/image/image03.png)
	
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
