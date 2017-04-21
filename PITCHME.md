
# 面向对象设计原则

❓有哪些面向对象编程的概念 <!-- .element: class="fragment" -->

---

## 面向对象术语

* **封装**-把一个单元的内部实现隐藏起来，显示尽量少的接口，使变化局限于单元内部 
* **继承**-使用现有类的一种方式，子类可以直接复用父类的属性和行为 
* **代理**-使用现有类的另一种方式，将请求转派给已有类的对象 
* **多态**-不同类型的对象能响应同一个方法调用，并表现出各自不同的行为 

❓重要程度如何排序 <!-- .element: class="fragment" -->

<!--
封装->委托->继承
继承的问题
-->

---

# 应对变化和复杂

---

# 高内聚&低耦合

* **内聚**是一个系统里多个部分一起执行工作的度量，来获得比每个部分单独工作获得更好的结果 
* **耦合**是一个类、方法或者任何一个实体直接与另一个实体连接的度 

---

# SOLID原则 
[《敏捷软件开发 原则、模式与实践》](https://book.douban.com/subject/1140457/)     
<img src="https://img3.doubanio.com/lpic/s1671095.jpg" style="width: 250px;"/>    
作者: Robert C. Martin   
[✨一些软件设计的原则](http://coolshell.cn/articles/4535.html) <!-- .element: class="fragment" -->

+++

## 单一职责原则(Single Responsibility)

一个类只能因为一个原因改变

<!--
代码重复，不同“方向”的变化
-->

+++

## 开闭原则(Open/Close)

软件实体(类、方法，对象)对修改封闭，对扩展开放

<!--
面向接口编程，组合由于继承(LSP)
-->

+++

## 里氏替换原则(Liskov's Substitution)

子类不能破坏父类的类型定义

<!--
继承关系的开闭原则，契约
-->

+++

## 接口隔离原则(Interface Segregation)

客户不应该被强迫依赖它用不上的方法

+++

## 依赖倒置原则(Dependency Inversion)

A. 上层模块不应该依赖下层模块，两者之间应该依赖抽象而不是具体实现
B. 抽象不应该依赖于细节，细节应该依赖于抽象

---

## 设计模式
[《设计模式：可复用面向对象软件的基础》](https://book.douban.com/subject/1052241/)  
<img src="https://img3.doubanio.com/lpic/s1074361.jpg" style="width: 250px;"/>     
作者(GoF): Gamma Erich, Helm Richard, Johnson Ralph, Vlissides John.

---

## 设计模式分类

![](http://www.vincehuston.org/images/GoF_full_medium.png)  

**创建型模式**<!-- .element: class="fragment" --> **行为型模式**<!-- .element: class="fragment" --> **结构型模式**<!-- .element: class="fragment" --> **架构模式**<!-- .element: class="fragment" -->

---

## 创建型模式

* 单例模式
* 工厂方法模式
* 抽象工厂模式
* 建造者模式
* 原型模式

---

# 单例模式

+++

## 实现

```java
public class ToolManager {
    private static volatile ToolManager mToolManager;
    private static Object mObject = new Object();

    public static ToolManager getInstance(){
        if (mToolManager == null){
            synchronized (mObject) {
                if (mToolManager == null) {
                    mToolManager = new ToolManager();
                }
            }
        }
        return mToolManager;
    }
}
```

❓为什么要这样实现   <!-- .element: class="fragment" -->
<!--延迟加载的线程不安全，synchronized带来性能开销，双重检查锁定必须使用volatile(java 1.5)
-->  
[双重检查锁定与延迟初始化](http://www.infoq.com/cn/articles/double-checked-locking-with-delay-initialization) <!-- .element: class="fragment" -->

+++

## 最短的线程安全的实现
```java
public class InstanceFactory {
    private static class InstanceHolder {
        public static Instance instance = new Instance();
    }

    public static Instance getInstance() {
        return InstanceHolder.instance;
    }
}
```

+++

## 运用

[👿为什么单例模式是邪恶的](http://www.cnblogs.com/nomoneynowife/p/3719031.html)  <!-- .element: class="fragment" -->
* 和全局变量没什么区别<!-- .element: class="fragment" --> 可能导致内存泄漏 <!-- .element: class="fragment" -->   
* 即负责创建逻辑也限制创建的数量，违反SRP<!-- .element: class="fragment" --> 
<!--工厂模式与建造者模式不违反SRP-->
* 调用的地方紧耦合，违反OCP<!-- .element: class="fragment" --> 无法在测试时方便的替换 <!-- .element: class="fragment" -->
* 全局状态处于未知状态<!-- .element: class="fragment" --> 测试无法独立运行 <!-- .element: class="fragment" -->

✨仔细分析上下文，确定无上诉设计的需要(确定是全局仅需要一个实例); 或者改用其他设计模式 <!-- .element: class="fragment" -->

---

# 简单工厂模式

把创建分离到单独的方法

+++ 

## 实现(待续)
```java
interface Door {
    public float getWidth();
    public float getHeight();
}

class WoodenDoor implements Door {
    protected float width;
    protected float height;

    public WoodenDoor(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public float getWidth() {
        return this.width;
    }

    public float getHeight() {
        return this.height;
    }
}
```

+++

## 实现(完)
```java
class DoorFactory {
   public static Door makeDoor(width, height) {
       return new WoodenDoor(width, height);
   }
}
Door door = DoorFactory.makeDoor(100, 200);
```

+++

## 运用

创建接口的实例，隐藏具体的接口实现

IDE重构: https://www.jetbrains.com/help/idea/2016.3/replace-constructor-with-factory-method.html

---

# 工厂方法模式

把生成逻辑移交给子类

+++

## 实现(待续)

```java
interface Interviewer {
    public void askQuestions();
}

public class Developer implements Interviewer {
    public void askQuestions() {
        System.out.prinln("Asking about design patterns!");
    }
}

public class CommunityExecutive implements Interviewer {
    public void askQuestions() {
        System.out.prinln("Asking about community building");
    }
}
```

+++

## 实现(待续)

```java
public abstract class HiringManager {

    // 工厂方法(抽象)
    abstract public Interviewer makeInterviewer();

    public void takeInterview() {
        Interviewer interviewer = this.makeInterviewer();
        interviewer.askQuestions();
    }
}
```
+++


## 实现(待续)

```java
public class DevelopmentManager extends HiringManager {
    public Interviewer makeInterviewer() {
        return new Developer();
    }
}

public class MarketingManager extends HiringManager {
    public Interviewer makeInterviewer() {
        return new CommunityExecutive();
    }
}
```

+++

## 实现(完)

```java
DevelopmentManager devManager = new DevelopmentManager();
devManager.takeInterview(); // 输出: Asking about design patterns

MarketingManager marketingManager = new MarketingManager();
marketingManager.takeInterview(); // 输出: Asking about community building.

```
+++

## 运用

工厂本身也是抽象的

---

# 抽象工厂模式

一个制造工厂的工厂；一个工厂把独立但是相关／有依赖性的工厂进行分类，但是不需要给出具体的类。

+++

## 实现(待续)
```java
interface Door {
    public void getDescription();
}

public class WoodenDoor implements Door {
    public void getDescription() {
        System.out.println("I am a wooden door");
    }
}

class IronDoor implements Door {
    public void getDescription() {
        System.out.println("I am an iron door");
    }
}
```

+++

## 实现(待续)
```java
interface DoorFittingExpert {
    public void getDescription();
}

class Welder implements DoorFittingExpert {
    public void getDescription() {
        System.out.println("I can only fit iron doors");
    }
}

class Carpenter implements DoorFittingExpert {
    public void getDescription() {
        System.out.println("I can only fit wooden doors");
    }
}
```

+++

## 实现(待续)

```java
interface DoorFactory {
    public Door makeDoor();
    public DoorFittingExpert makeFittingExpert();
}

// 木头工厂返回木门和木匠
class WoodenDoorFactory implements DoorFactory {
    public Door makeDoor() {
        return new WoodenDoor();
    }

    public DoorFittingExpert makeFittingExpert() {
        return new Carpenter();
    }
}

// 铁门工厂返回铁门和对应安装专家
class IronDoorFactory implements DoorFactory {
    public Door makeDoor() {
        return new IronDoor();
    }

    public DoorFittingExpert makeFittingExpert() {
        return new Welder();
    }
}
```

+++

## 实现(完)

```java
DoorFactory woodenFactory = new WoodenDoorFactory();

Door door = woodenFactory。makeDoor();
DoorFittingExpert expert = woodenFactory.makeFittingExpert();

door.getDescription();  // 输出: I am a wooden door
expert.getDescription(); // 输出: I can only fit wooden doors

// 铁门工厂也一样
DoorFactory ironFactory = new IronDoorFactory();

Door door = ironFactory.makeDoor();
DoorFittingExpert expert = ironFactory.makeFittingExpert();

door.getDescription();  // 输出: I am an iron door
expert.getDescription(); // 输出: I can only fit iron doors

```

+++

## 运用

有一组相关的对象需要相继创建

---

# 建造者模式

创建不同特点的对象而避免构造函数污染。当一个对象都多种特点的时候比较实用。或者在创造逻辑里有许多步骤的时候。

+++

## 👎[伸缩套管式构造函数](http://codethataint.com/blog/telescoping-constructor-pattern-java/)
```java
Pizza(int size) { ... }        
Pizza(int size, boolean cheese) { ... }    
Pizza(int size, boolean cheese, boolean pepperoni) { ... }    
Pizza(int size, boolean cheese, boolean pepperoni, boolean bacon) { ... }
```
#### 👎解决方法之一
```java
Pizza pizza = new Pizza(12);
pizza.setCheese(true);
pizza.setPepperoni(true);
pizza.setBacon(true);
```
+++

## 👍解决方法(建造者)
```java
public class Pizza {
  private int size;
  private boolean cheese;
  private boolean pepperoni;
  private boolean bacon;
 
  public static class Builder {
    //required
    private final int size;
 
    //optional
    private boolean cheese = false;
    private boolean pepperoni = false;
    private boolean bacon = false;
 
    public Builder(int size) {
      this.size = size;
    }
 
    public Builder cheese(boolean value) {
      cheese = value;
      return this;
    }
    ...
    public Pizza build() {
      return new Pizza(this);
    }
  }
 
  private Pizza(Builder builder) {
    size = builder.size;
    cheese = builder.cheese;
    pepperoni = builder.pepperoni;
    bacon = builder.bacon;
  }
}
...
Pizza pizza = new Pizza.Builder(12)
                       .cheese(true)
                       .pepperoni(true)
                       .bacon(true)
                       .build();
```

+++

## 运用

- Android: [ShareCompat.IntentBuilder](https://developer.android.com/reference/android/support/v4/app/ShareCompat.IntentBuilder.html) [NotificationCompat.Builder](https://developer.android.com/reference/android/support/v4/app/NotificationCompat.Builder.html)
- IDE支持：https://www.jetbrains.com/help/idea/2016.3/replace-constructor-with-builder.html

---

# 原型模式

通过"克隆"已有的对象来创建新对象

+++

## 实现(待续)
```java
    /**
     * Retrieves a map of extended data from the intent.
     *
     * @return the map of all extras previously added with putExtra(),
     * or null if none have been added.
     */
    public Bundle getExtras() {
        return (mExtras != null)
                ? new Bundle(mExtras)
                : null;
    }
```

+++

## 实现(待续)
```java
public final class Bundle extends BaseBundle implements Cloneable, Parcelable {
    /**
     * Constructs a Bundle containing a copy of the mappings from the given
     * Bundle.
     *
     * @param b a Bundle to be copied.
     */
    public Bundle(Bundle b) {
        super(b);
        mFlags = b.mFlags;
    }

    /**
     * Clones the current Bundle. The internal map is cloned, but the keys and
     * values to which it refers are copied by reference.
     */
    @Override
    public Object clone() {
        return new Bundle(this);
    }
}
```

+++

# 实现(完)

```java
public class BaseBundle {
/**
     * Constructs a Bundle containing a copy of the mappings from the given
     * Bundle.
     *
     * @param b a Bundle to be copied.
     */
    BaseBundle(BaseBundle b) {
        if (b.mParcelledData != null) {
            if (b.isEmptyParcel()) {
                mParcelledData = NoImagePreloadHolder.EMPTY_PARCEL;
            } else {
                mParcelledData = Parcel.obtain();
                mParcelledData.appendFrom(b.mParcelledData, 0, b.mParcelledData.dataSize());
                mParcelledData.setDataPosition(0);
            }
        } else {
            mParcelledData = null;
        }

        if (b.mMap != null) {
            mMap = new ArrayMap<>(b.mMap);
        } else {
            mMap = null;
        }

        mClassLoader = b.mClassLoader;
    }
}
```

---

# 依赖注入

+++

## 使用了工厂方法
```java
public class ServiceApi {
private Client client;
public ServiceApi(){
client = HTTPClientFactory.createHTTPClient();
}
}
```
> 有什么问题？
<!-- .element: class="fragment" -->

* “硬编码”初始化违反开闭原则 <!-- .element: class="fragment" -->
* 负责对象创建（甚至声明周期管理）违反单一职责原则 <!-- .element: class="fragment" -->

+++

所有的逻辑都被代码（ServiceApi）**控制**，使用它只能被动的接受它的依赖（HTTPClientFactory.createHTTPClient()返回的具体Client）

+++

## 寻找“接缝”

"**接缝**是指程序中的一些特殊的点，在这些点上你无需做任何修改就可以达到改动程序行为的目的。" --《修改代码的艺术》

+++

## 接缝在哪里？

```java
public class ServiceApi {
private Client client;
public ServiceApi(){
client = HTTPClientFactory.createClient();
}
}
```
> 需要增加接缝
<!-- .element: class="fragment" -->

+++

## 1. 改造构造方法

```java
public class ServiceApi {
private final Client client;
public ServiceApi(Client client){
this.client = client;
}
}
```

适用于：依赖需要反复使用，且不需要变化
<!-- .element: class="fragment" -->

+++

## 2. 增加Setter

```java
public class ServiceApi {
private Client client;
public ServiceApi(){
}
public void setClient(Client client) {
this.client = client;
}
}
```

适用于：依赖需要反复使用，且可能发生变化
<!-- .element: class="fragment" -->

⚠️注意空安全，使用时需要增加Null检查
<!-- .element: class="fragment" -->

+++

## 3. 通过继承改变行为

```java
public class ServiceApi {
protected Client client;
public ServiceApi(){
client = HTTPClientFactory.createClient();
}
}
public class MyServiceApi extends ServiceApi {
public MyServiceApi(){
client = RPCClientFactory.createClient();
}
}
```

适用于：依赖无法修改（通常是平台或第三方的）但可以继承
<!-- .element: class="fragment" -->

⚠️继承是紧耦合的，不要违反里氏替换原则
<!-- .element: class="fragment" -->

+++

## 4. 通过形参改变行为

```java
public class ServiceApi {
public JSONObject get(Client client) {
return client.get(...);
}
}
```

适用于：依赖用完即“弃”
<!-- .element: class="fragment" -->

+++

## 控制反转

对象在被创建的时候，由一个调控系统内所有对象的外界实体，将其所依赖的对象的引用传递给它，即依赖被注入到对象中。
<!-- .element: class="fragment" -->

> "不要给我们打电话，我们会打给你"。--好莱坞原则
<!-- .element: class="fragment" -->

> “库”和“框架”的区别
<!-- .element: class="fragment" -->

+++

## 依赖注入

我们需要一个调控系统，这个调控系统中我们存放一些对象的实体，或者对象的描述; 在类A的实例创建过程中即创建了A依赖的B对象，通过类型或名称来判断将不同的对象注入到不同的属性中。

+++

## Java中常见的依赖注入方式

* 构造方法注入 <!-- .element: class="fragment" -->
* Setter注入 <!-- .element: class="fragment" -->
* 方法形参注入 <!-- .element: class="fragment" -->
* 属性(成员变量)注入 <!-- .element: class="fragment" -->

+++

## 依赖注入容器

> 可以自己实现注入，但是...
<!-- .element: class="fragment" -->

定义接口，实现类，实现工厂方法，实现单例，实现builder，利用反射进行注入，orz
<!-- .element: class="fragment" -->

+++

## 常见的Java注入容器

* PicoContainer <!-- .element: class="fragment" -->
* Guice <!-- .element: class="fragment" -->
* Spring DI <!-- .element: class="fragment" -->
* Dagger 2 <!-- .element: class="fragment" -->
* ... <!-- .element: class="fragment" -->

+++

## 何时使用依赖注入

* 可以遇见到依赖的实现会发生改变
<!-- .element: class="fragment" -->
> 比如：为了单元测试的时候能够换成mock
<!-- .element: class="fragment" -->
* 依赖的构造逻辑不应该放在使用它的地方
<!-- .element: class="fragment" -->
> 比如：由配置文件决定，或者运行时动态决定
<!-- .element: class="fragment" -->
* 有一个“上帝”知道所有依赖之间的关系和生命周期（对象（依赖）图）
<!-- .element: class="fragment" -->
> 比如：这个“上帝”往往是领域（业务）模型，和具体UI界面无关
<!-- .element: class="fragment" -->

+++

## 选择依赖注入容器(待续)

* **功能**
<!-- .element: class="fragment" -->
> 延迟初始化、异步、作用域...
<!-- .element: class="fragment" -->
* **可读性**
<!-- .element: class="fragment" -->
> 是否能通过代码清晰的累积对象图（依赖关系）
<!-- .element: class="fragment" -->
* **性能**
<!-- .element: class="fragment" -->
> 运行时注入还是编译时注入，方法数
<!-- .element: class="fragment" -->

+++

## 选择依赖注入容器(完)

* **易用性**
<!-- .element: class="fragment" -->
> IDE支持，是否能在注入处跳转到工厂方法，错误信息是否详细
<!-- .element: class="fragment" -->
* **是否和框架绑定**
<!-- .element: class="fragment" -->
> Spring建议使用String IoC
<!-- .element: class="fragment" -->

+++

## 依赖注入容器对比

[**通用容器对比**](https://stackoverflow.com/questions/2026016/google-guice-vs-picocontainer-for-dependency-injection)  
<!-- .element: class="fragment" -->
[**Android容器对比**](
http://blog.nimbledroid.com/2016/03/07/performance-of-dependency-injection-libraries.html)  
<!-- .element: class="fragment" -->

> 以Dagger 2为例介绍依赖注入框架
<!-- .element: class="fragment" -->

+++

## Dagger 2

* @Inject：需要注入依赖的地方，Dagger 会构造一个该类的实例并满足它所需要的依赖
<!-- .element: class="fragment" -->
* @Module：依赖的提供者，Module 类中的方法专门提供依赖，并用@Provides注解标记
<!-- .element: class="fragment" -->
* @Component：依赖的注入者，是@Inject和@Module的桥梁，它从@Module中获取依赖并注入给@Inject
<!-- .element: class="fragment" -->


+++

## @Inject(构造方法注入)

```java
public class LoginActivityPresenter {
    
    private LoginActivity loginActivity;
    private UserDataStore userDataStore;
    private UserManager userManager;
    
    @Inject
    public LoginActivityPresenter(LoginActivity loginActivity,
                                  UserDataStore userDataStore,
                                  UserManager userManager) {
        this.loginActivity = loginActivity;
        this.userDataStore = userDataStore;
        this.userManager = userManager;
    }
}
```
> 被注解的类也可以作为对象图的依赖
<!-- .element: class="fragment" -->

+++

## @Inject(属性注入)

```java
public class SplashActivity extends AppCompatActivity {
    
    @Inject
    LoginActivityPresenter presenter;
    @Inject
    AnalyticsManager analyticsManager;
    
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getAppComponent().inject(this);
    }
}
```

+++
## @Module & @Provides
```java
@Module
public class GithubApiModule {
    
    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(60 * 1000, TimeUnit.MILLISECONDS);
        okHttpClient.setReadTimeout(60 * 1000, TimeUnit.MILLISECONDS);
        return okHttpClient;
    }

    @Provides
    @Singleton
    RestAdapter provideRestAdapter(Application application, OkHttpClient okHttpClient) {
        RestAdapter.Builder builder = new RestAdapter.Builder();
        builder.setClient(new OkClient(okHttpClient))
               .setEndpoint(application.getString(R.string.endpoint));
        return builder.build();
    }
}
```
> 有内聚的一组工厂方法
<!-- .element: class="fragment" -->

+++

## @Component

```java

@Singleton
@Component(
    modules = {
        AppModule.class,
        GithubApiModule.class
    }
)
public interface AppComponent {

    void inject(GithubClientApplication githubClientApplication);

    Application getApplication();

    AnalyticsManager getAnalyticsManager();

    UserManager getUserManager();
}
```
> 需要哪些"工厂",哪些对象可以被注入，提供了哪些依赖供注入
<!-- .element: class="fragment" -->

+++

## 注入

```java
public class GithubClientApplication extends Application {

    private AppComponent appComponent;

    public static GithubClientApplication get(Context context) {
        return (GithubClientApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        //Global dependencies graph is created here
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .githubApiModule(new GithubApiModule()) //Can be removed because of no-arg constructor
                .build();
    }

    //Just a helper to provide appComponent to local components which depend on it
    public AppComponent getAppComponent() {
        return appComponent;
    }
}
```

+++

## 完整例子

https://frogermcs.github.io/dependency-injection-with-dagger-2-the-api/

+++

## 如何测试

单元测试时不需要注入容器，直接手动注入mock


---


## 结构型模式

* 适配器模式 Adapter
* 桥接模式 Bridge
* 组合模式 Composite
* 门面模式 Facade
* 享元模式 Flyweight
* 代理模式 Proxy
* 装饰器模式 Decorator

---

# 适配器模式

适配器模式让你封装一个不兼容的对象到一个适配器，来兼容其他类。

+++

## 实现(待续)

```java
interface Figure {
    public void draw();
}

class Line implements Figure {
    public void draw() {
        System.out.println("Draw: Line");
    }
}

new Line().draw();
```

+++

## 实现(一)

```java
// !不兼容的实现
class Rectangle {
    public void drawRectangle() {
        System.out.println("DrawRectangle: Rectangle");
    }
}

// “包装”起来变成兼容的
class AdapterRectangle implements Figure {
    private Rectangle rectangle = new Rectangle();
    public void draw() {
        rectangle.drawRectangle();
    }
}

figures = new Figure[2];
figures[0] = new Line();
figures[1] = new AdapterRectangle();

for (Figure figure: figures) {
    figure.draw();
}
```
> 对象适配器--组合
<!-- .element: class="fragment" -->
+++

## 实现(二)

```java
class AdapterRectangle extends Rectangle implements Figure {
    public void draw() {
        this.drawRectangle();
    }
}
```
> 类适配器--继承
<!-- .element: class="fragment" -->
+++

## 应用

❓Android的Adaper是适配器吗？
<!-- .element: class="fragment" -->

---

# 桥接模式

桥接模式倾向构造而非继承。实现细节被从一个层推送到另一个对象的另一层。

+++

## 实现(待续)
```java
// 一组形状的抽象与实现
abstract class Shape {
    protected DrawProgram drawProgram;

    public Shape(DrawProgram drawProgram) {
        this.drawProgram = drawProgram;
    }

    public abstract String draw();
}

class Line extends Shape {
    public Line(DrawProgram drawProgram) {
        super(drawProgram);
    }

    public String draw() {
        return drawProgram.drawLine();
    }
}

class Circle extends Shape {
    public Circle(DrawProgram drawProgram) {
        super(drawProgram);
    }

    public String draw() {
        return drawProgram.drawCircle();
    }
}
```

+++

## 实现(待续)
```java
// 一组绘制的抽象与实现
abstract class DrawProgram {
    public abstract String drawCircle();

    public abstract String drawLine();
}

class DrawProgram1 extends DrawProgram {
    public String drawCircle() {
        return "DrawProgram1: drawCircle()";
    }

    public String drawLine() {
        return "DrawProgram1: drawLine()";
    }
}

class DrawProgram2 extends DrawProgram {
    public String drawCircle() {
        return "DrawProgram2: drawCircle()";
    }

    public String drawLine() {
        return "DrawProgram2: drawLine()";
    }
}
```

+++

## 实现(完)

```java
Shape[] shapes = new Shape[] {
    new Circle(new DrawProgram1()),
        new Circle(new DrawProgram2()),
        new Line(new DrawProgram1()),
        new Line(new DrawProgram2())
}
for (Shape shape: shapes) {
    System.out.println(shape.draw());
}
```

+++

## 应用(待续)
* Android中的Adapter
<!-- .element: class="fragment" -->
![Adapter](https://i.imgur.com/mk82Jd2.jpg)
<!-- .element: class="fragment" -->

+++

## 应用(待续)

![BaseAdapter](http://www.intertech.com/Blog/wp-content/uploads/2014/06/HeirarchyOfAdapter.png)

+++

## 应用(完)

![AdapterView](http://www.intertech.com/Blog/wp-content/uploads/2014/06/AdapterViewHierarchy.png)

---

# 组合模式

组合模式让调用者可以用统一的模式对待不同的对象

+++

## 实现(待续)

```java
abstract class Figure {
    public String draw() {
        return "";
    }
}

class Line extends Figure {
    public String draw() {
        return "Draw: Line";
    }
}

class Point extends Figure {
    public String draw() {
        return "Draw: Point";
    }
}
```

+++

## 实现(待续)

```java
import java.util.ArrayList;
import java.util.ListIterator;

class Composite extends Figure {
    private ArrayList < Figure > figures = new ArrayList < Figure > ();

    public String draw() {
        String s = "";

        ListIterator < Figure > iterator = figures.listIterator();

        while (iterator.hasNext()) {
            s = s + " " + iterator.next().draw();
        }

        return s;
    }

    public void remove(Figure figure) {
        figures.remove(figure);
    }

    public void add(Figure figure) {
        figures.add(figure);
    }
}
```

+++

## 实现(完)

```java
Figure point = new Point();
Figure line = new Line();

Figure composite = new Composite()
composite.add(point);
composite.add(line)

System.out.println(composite.draw())
composite.remove(point)
System.out.println(composite.draw());
```

+++

## 应用

* Android的ViewGroup
<!-- .element: class="fragment" -->
![ViewGroup](http://abhiandroid.jobxfryqt.netdna-cdn.com/ui/wp-content/uploads/2016/06/Basic-UI-user-interface-ViewGroup-and-View-Diagram.jpg)
<!-- .element: class="fragment" -->

---

# 门面模式

门面模式提供了一个复杂子系统的简单接口。

+++

## 实现(待续)

```java
class Caffeine {
    protected String addCaffeine() {
        return "Caffeine";
    }
}

class Sugar {
    protected String addSugar() {
        return "Sugar";
    }
}

class Water {
    protected String addWater() {
        return "Water";
    }
}
```

+++

## 实现(待续)

```java
class Facade {
    protected String makeCoffee() {
        Sugar s = new Sugar();
        Caffeine c = new Caffeine();
        Water w = new Water();

        return "Coffee = " + w.addWater() + " + " + c.addCaffeine() + " + " + s.addSugar();
    }
}
```

+++

## 实现(完)
```java
Facade f = new Facade();

System.out.println(f.makeCoffee());
```

+++
### 应用

Android中的Context.getString(int resId)


---
# 享元模式

通过尽可能分享相似的对象，来将内存使用或计算开销降到最低。

+++

## 实现(待续)

```java
import java.util.HashMap;
import java.util.Map;

class Menu {
    public Map < String, CoffeeFlavour > coffeeFlavours = new HashMap < String, CoffeeFlavour > ();

    public CoffeeFlavour lookup(String flavorName) {
        if (!coffeeFlavours.containsKey(flavorName)) {
            coffeeFlavours.put(flavorName, new CoffeeFlavour(flavorName));
        }

        return coffeeFlavours.get(flavorName);
    }
}

class CoffeeFlavour {
    private final String flavourName;

    public CoffeeFlavour(String flavourName) {
        this.flavourName = flavourName;
    }

    public String getFlavourName() {
        return this.flavourName;
    }
}
```

+++

## 实现(待续)

```java
class Order {
public final int tableNumber;
public final CoffeeFlavour coffeeFlavour;

public Order(int tableNumber, CoffeeFlavour coffeeFlavour) {
this.tableNumber = tableNumber;
this.coffeeFlavour = coffeeFlavour;
}

public String toString() {
return "Order at table: " + tableNumber + ", coffee flavour: " + coffeeFlavour.getFlavourName();
}
}

```

+++

## 实现(待续)

```java
import java.util.ArrayList;
import java.util.ListIterator;

class CoffeeShop {
    public Menu menu = new Menu();
    public ArrayList < Order > orders = new ArrayList < Order > ();

    public void getOrder(int tableNumber, String coffeeFlavour) {
        orders.add(new Order(tableNumber, menu.lookup(coffeeFlavour)));
    }

    public String toString() {
        String report = "";
        ListIterator < Order > orderIterator = orders.listIterator();

        while (orderIterator.hasNext()) {
            report += orderIterator.next().toString() + "\n";
        }

        return report;
    }
}
```

+++

## 实现(完)

```java
CoffeeShop shop = new CoffeeShop();

Order order1 = shop.getOrder(1, "espresso");
Order order5 = shop.getOrder(7, "macchiato")
Order order7 = shop.getOrder(5, "macchiato")

assert order5.coffeeFlavour == order7.coffeeFlavour
```

+++

## 应用(待续)

```java
Integer number1 = 100;
Integer number2 = 100;

assert number1 == number2 //??
```
<!-- .element: class="fragment" -->


```java
Integer number3 = 1000;
Integer number4 = 1000;

assert number3 == number4 //??
```
<!-- .element: class="fragment" -->

+++

## 应用(完)

![IntegerCache](http://www.injavawetrust.com/wp-content/uploads/integer-pool.png)
<!-- .element: class="fragment" -->

* [Java中的IntergerCache](http://www.importnew.com/18884.html)
<!-- .element: class="fragment" -->
---

# 代理模式

使用代理模式，一个类表现出了另一个类的功能。

+++

## 实现(待续)

```java
abstract class File {
    protected boolean isProtected = true;

    public abstract String read();
}

class PublicFile extends File {
    public String read() {
        return "Read public File";
    }
}
```

+++

## 实现(待续)

```java
class ProxyProtectedFile extends File {
    private File file = null;

    public ProxyProtectedFile() {
        this.file = new PublicFile();
    }

    public String read() {
        if (isProtected)
            return "File is protected";
        else
            return this.file.read();
    }

    public void setProtection(boolean isProtected) {
        this.isProtected = isProtected;
    }
}
```

+++

## 实现(完)

```java
File[] files = new File[] {
    new ProxyProtectedFile(),
        new PublicFile()
};

for (File file: files) {
    System.out.println(file.read());
}
```

+++

## 应用(待续)

* [Android中的AIDL](https://developer.android.com/guide/components/aidl.html)
<!-- .element: class="fragment" -->

![AIDL](http://www.linuxtopia.org/online_books/android/devguide/images/binder_rpc.png)
<!-- .element: class="fragment" -->


+++

## 应用(完)

* [Java动态代理](http://www.infoq.com/cn/articles/cf-java-reflection-dynamic-proxy)
<!-- .element: class="fragment" -->

---

# 装饰器模式

装饰器模式让你能在运行时动态地改变一个对象的表现，通过把它们封装到一个装饰器类。

+++

## 实现(待续)
```java
abstract class Coffee {
    public abstract double getCost();

    public abstract String getIngredients();
}

class SimpleCoffee extends Coffee {
    public double getCost() {
        return 1;
    }

    public String getIngredients() {
        return "SimpleCoffee";
    }
}
```

+++

## 实现(待续)
```java

abstract class CoffeeDecorator extends Coffee {
    private final Coffee decorator;

    public CoffeeDecorator(Coffee decorator) {
        this.decorator = decorator;
    }

    public double getCost() {
        return decorator.getCost();
    }

    public String getIngredients() {
        return decorator.getIngredients();
    }
}
```

+++

## 实现(待续)

```java

class Milk extends CoffeeDecorator {
    public Milk(Coffee decorator) {
        super(decorator);
    }

    public double getCost() {
        return super.getCost() + 0.5;
    }

    public String getIngredients() {
        return super.getIngredients() + ", " + "Milk";
    }
}

class Sugar extends CoffeeDecorator {
    public Sugar(Coffee decorator) {
        super(decorator);
    }

    public String getIngredients() {
        return super.getIngredients() + ", " + "Sugar";
    }
}
```

+++

## 实现(完)

```java
Coffee c = new SimpleCoffee();
Coffee custom = new Sugar(new Milk(c))

System.out.println(custom.getIngredients());
System.out.println(custom.getCost());
```

+++

## 应用(待续)

* [OKHttp的Interceptor](https://github.com/square/okhttp/wiki/Interceptors)
<!-- .element: class="fragment" -->  

<img src="https://raw.githubusercontent.com/wiki/square/okhttp/interceptors@2x.png" style="width: 300px;"/>    
<!-- .element: class="fragment" -->


+++

## 应用(完)

* Java IO  
<!-- .element: class="fragment" -->
![Java中的IO](http://img.it610.com/image/product/c03c6d9c7eaa4f18b1225f4dc176fc96.jpg)
<!-- .element: class="fragment" -->

---

## 行为型模式(待续)

* 职责链模式
* 命令模式
* 迭代器模式
* 中介者模式
* 观察者模式

+++

## 行为型模式(待续)

* 备忘录模式
* 访问者模式
* 策略模式
* 状态模式
* 模板方法模式

❓常见的行为型模式有哪些？<!-- .element: class="fragment" -->

+++

## 行为型模式(待续)

* 职责链模式
* **命令模式**
* **迭代器模式**
* ~~中介者模式~~
* **观察者模式**

+++

## 行为型模式(完)

* 备忘录模式
* ~~访问者模式~~
* **策略模式**
* ~~状态模式~~
* 模板方法模式

---

## 职责链模式

发送者将待处理的对象发给一串处理者的，对象在处理者一次传递，直到有处理者完成了对象的处理

+++

## 实现(待续)

```java
interface Image {
    String process();
}

class IR implements Image {
    public String process() {
        return "IR";
    }
}

class LS implements Image {
    public String process() {
        return "LS";
    }
}
```
+++

## 实现(完)

```java
class Processor {
    private static final Random RANDOM = new Random();
    private static int nextID = 1;
    private int id = nextID++;

    public boolean execute(Image img) {
        if (RANDOM.nextInt(2) != 0) {
            System.out.println("   Processor " + id + " is busy");
            return false;
        }
        System.out.println("Processor " + id + " - " + img.process());
        return true;
    }
}
```

+++

## 实现(待续)

```java
public class ChainDemo {
    public static void main( String[] args ) {
        Image[] inputImages = {new IR(), new IR(), new LS(), new IR(), new LS(), new LS()};
        Processor[] processors = {new Processor(), new Processor(), new Processor()};
        for (int i=0, j; i < inputImages.length; i++) {
            System.out.println("Operation #" + (i + 1) + ":");
            j = 0;
            while (!processors[j].execute(inputImages[i])) {
                j = (j + 1) % processors.length;
            }
            System.out.println();
        }
    }
}
```

+++

## 应用

![android touch event dispatch](https://github.com/01org/mayloon-runtime/wiki/856px-Ontouch.png)<!-- .element: class="fragment" -->

+++

## 应用
![exception try catch](https://www.javatpoint.com/images/exceptionobject.JPG)<!-- .element: class="fragment" -->

+++

### 特点

1. 发送者只把对象发给第一个处理者，不关心最终由哪个处理者处理<!-- .element: class="fragment" -->
2. 对象通常情况下最终一定会得到处理（责任），比如有默认的处理机制兜底<!-- .element: class="fragment" -->
3. 责任“串”的形式可以是链表，也可以是组合模式的递归（后继处理者是当前处理者的容器），由发送者或者第三方创建或维护<!-- .element: class="fragment" -->
4. 处理者基类的处理方式就是把对象传给下一个处理者<!-- .element: class="fragment" -->

---

## 命令模式

封装可以执行的请求，接收者既不知道是谁发起的请求，也不知道请求的操作。

+++

## 实现(待续)

```java
interface Command {
    void execute();
}

class DomesticEngineer implements Command {
    public void execute() {
        System.out.println("take out the trash");
    }
}

class Politician implements Command {
    public void execute() {
        System.out.println("take money from the rich, take votes from the poor");
    }
}

class Programmer implements Command {
    public void execute() {
        System.out.println("sell the bugs, charge extra for the fixes");
    }
}
```

+++

## 实现(完)

```java
public class CommandDemo {
    public static List produceRequests() {
        List<Command> queue = new ArrayList<>();
        queue.add(new DomesticEngineer());
        queue.add(new Politician());
        queue.add(new Programmer());
        return queue;
    }

    public static void workOffRequests(List queue) {
        for (Object command : queue) {
            ((Command)command).execute();
        }
    }

    public static void main( String[] args ) {
        List queue = produceRequests();
        workOffRequests(queue);
    }
}
```

+++

## 应用

![android handler & message](https://i.stack.imgur.com/nwhYs.png)

+++

## 特点

1. 命令存放在队列中<!-- .element: class="fragment" -->
2. 大多数情况下命令是异步执行的<!-- .element: class="fragment" -->
2. 命令中包括接收者，接收者被调用的方法，以及参数，非常适合使用lambda<!-- .element: class="fragment" -->
3. 命令中一般会有一个“执行”方法，在合适的时机完成上述的方法调用<!-- .element: class="fragment" -->
4. 第三方来维护和调用命令<!-- .element: class="fragment" -->
5. 命令可以实现“执行”相逆的“撤销”方法<!-- .element: class="fragment" -->

---

## 迭代器模式

把不同的数据结构抽象成通用的接口，提供遍历数据结构中元素的能力

+++

## Demo

+++

## 小结

1. External/Explicit/Active Iterator<!-- .element: class="fragment" -->
2. Internal/Implicit/Passive Iterator，可读性更高，扩展性更好，性能不如外部迭代器<!-- .element: class="fragment" -->
3. 利用集合支持的高阶函数或lambda表达式<!-- .element: class="fragment" -->

---

## 观察者模式

把对象状态的变化通知到所有有兴趣的依赖对象

**Listener**<!-- .element: class="fragment" --> **Callback**<!-- .element: class="fragment" --> **Observer**<!-- .element: class="fragment" --> **Event**<!-- .element: class="fragment" --> **...**<!-- .element: class="fragment" -->

+++

## 实现(待续)

```java
abstract class Observer {
    protected Subject subject;
    public abstract void update();
}
```

+++

## 实现(待续)

```java

class Subject {
    private List<Observer> observers = new ArrayList<>();
    private int state;

    public void add(Observer o) {
        observers.add(o);
    }

    public int getState() {
        return state;
    }

    public void setState(int value) {
        this.state = value;
        execute();
    }

    private void execute() {
        for (Observer observer : observers) {
            observer.update();
        }
    }
}
```

+++

## 实现(待续)

```java
class HexObserver extends Observer {
    public HexObserver(Subject subject) {
        this.subject = subject;
        this.subject.add(this);
    }

    public void update() {
        System.out.print(" " + Integer.toHexString(subject.getState()));
    }
}
```

+++

## 实现(待续)

```java
class OctObserver extends Observer {
    public OctObserver(Subject subject) {
        this.subject = subject;
        this.subject.add( this );
    }

    public void update() {
        System.out.print(" " + Integer.toOctalString(subject.getState()));
    }
}
```

+++

## 实现(待续)

```java
class BinObserver extends Observer {
    public BinObserver(Subject subject) {
        this.subject = subject;
        this.subject.add(this);
    }

    public void update() {
        System.out.print(" " + Integer.toBinaryString(subject.getState()));
    }
}
```

+++

## 实现(完)

```java

public class ObserverDemo {
    public static void main( String[] args ) {
        Subject sub = new Subject();
        // Client configures the number and type of Observers
        new HexObserver(sub);
        new OctObserver(sub);
        new BinObserver(sub);
        Scanner scan = new Scanner(System.in);
        for (int i = 0; i < 5; i++) {
            System.out.print("\nEnter a number: ");
            sub.setState(scan.nextInt());
        }
    }
}
```

+++

## 应用

![eventbus]https://raw.githubusercontent.com/greenrobot/EventBus/master/EventBus-Publish-Subscribe.png

http://greenrobot.org/eventbus/

+++

## 小结

1. 广播+异步，特别留意性能<!-- .element: class="fragment" -->
2. 推模式(状态被推送给Observer)和拉模式(Observer自己查询状态)<!-- .element: class="fragment" -->

---

## 响应式编程

迭代器+观察者

http://reactivex.io/

+++

![reactive](http://jonalvarezz.github.io/presentation-reactive-programming/images/reactivity.png)

+++

## DEMO

---

## 备忘录模式

把对象恢复到之前记录的状态

三种角色：
1. Originator: 需要保存状态的对象，需要实现保存和恢复两个接口<!-- .element: class="fragment" -->
2. Memento: 对象的状态存放的对象，每一个状态都有独立的标记；状态保存使用通用的方式，如常见的对象序列化/反序列化机制<!-- .element: class="fragment" -->
3. Caretaker：知道什么时机记录Originator对象状态和恢复，并使用Memento来存放或者恢复Originator的状态<!-- .element: class="fragment" -->

+++

## 实现(待续)

```java
class Memento {
    private String state;

    public Memento(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
```

+++

## 实现(待续)

```java
class Originator {
    private String state;
   /* lots of memory consumptive private data that is not necessary to define the
    * state and should thus not be saved. Hence the small memento object. */

    public void setState(String state) {
        System.out.println("Originator: Setting state to " + state);
        this.state = state;
    }

    public Memento save() {
        System.out.println("Originator: Saving to Memento.");
        return new Memento(state);
    }
    public void restore(Memento m) {
        state = m.getState();
        System.out.println("Originator: State after restoring from Memento: " + state);
    }
}
```

+++

## 实现(待续)

```java
class Caretaker {
    private ArrayList<Memento> mementos = new ArrayList<>();

    public void addMemento(Memento m) {
        mementos.add(m);
    }

    public Memento getMemento() {
        return mementos.get(1);
    }
}
```

+++

## 实现(完)

```java
public class MementoDemo {
    public static void main(String[] args) {
        Caretaker caretaker = new Caretaker();
        Originator originator = new Originator();
        originator.setState("State1");
        originator.setState("State2");
        caretaker.addMemento( originator.save() );
        originator.setState("State3");
        caretaker.addMemento( originator.save() );
        originator.setState("State4");
        originator.restore( caretaker.getMemento() );
    }
}
```

+++

## 应用

![android activity state](https://developer.android.com/images/fundamentals/restore_instance.png)

+++

## 问题

❓Originator, Memento和Caretaker分别是哪个具体的对象?

---

## 策略模式&模板方法模式

策略模式：实现一组算法，每一种算法都封装成一个对象，相互可以替换

模板方法模式：基类实现好算法步骤，子类通过重写步骤实现不同的算法

+++

## 实现(待续)

```java
// 1. Define the interface of the algorithm
interface Strategy {
    void solve();
}
```

+++

## 实现(待续)

```java
// 2. Bury implementation
@SuppressWarnings("ALL")
abstract class StrategySolution implements Strategy {
    // 3. Template Method
    public void solve() {
        start();
        while (nextTry() && !isSolution()) {}
        stop();
    }

    abstract void start();
    abstract boolean nextTry();
    abstract boolean isSolution();
    abstract void stop();
}
```

+++

## 实现(待续)

```java
class FOO extends StrategySolution {
    private int state = 1;

    protected void start() {
        System.out.print("Start  ");
    }

    protected void stop() {
        System.out.println("Stop");
    }

    protected boolean nextTry() {
        System.out.print("NextTry-" + state++ + "  ");
        return true;
    }

    protected boolean isSolution() {
        System.out.print("IsSolution-" + (state == 3) + "  ");
        return (state == 3);
    }
}
```

+++

## 实现(待续)

```java
// 2. Bury implementation
abstract class StrategySearch implements Strategy {
    // 3. Template Method
    public void solve() {
        while (true) {
            preProcess();
            if (search()) {
                break;
            }
            postProcess();
        }
    }

    abstract void preProcess();
    abstract boolean search();
    abstract void postProcess();
}
```

+++

## 实现(待续)

```java
@SuppressWarnings("ALL")
class BAR extends StrategySearch {
    private int state = 1;

    protected void preProcess()  {
        System.out.print("PreProcess  ");
    }

    protected void postProcess() {
        System.out.print("PostProcess  ");
    }

    protected boolean search() {
        System.out.print("Search-" + state++ + "  ");
        return state == 3 ? true : false;
    }
}
```

+++

## 实现(待续)

```java
// 4. Clients couple strictly to the interface
public class StrategyDemo {
    // client code here
    private static void execute(Strategy strategy) {
        strategy.solve();
    }

    public static void main( String[] args ) {
        Strategy[] algorithms = {new FOO(), new BAR()};
        for (Strategy algorithm : algorithms) {
            execute(algorithm);
        }
    }
}
```

+++

## 小结

1. 面向接口编程、lambda等形式就是纯粹的策略模式<!-- .element: class="fragment" -->
2. 工厂方法就是一种模板方法模式<!-- .element: class="fragment" -->

+++

## 应用

![android layout class hierarchy](http://www.itcsolutions.eu/wp-content/uploads/2011/08/Part_of__Android_View_Hierarchy.png)

---

## 其他模式
* ~~中介者模式~~
* ~~访问者模式~~
* ~~状态模式~~

---

## 总结

+++

## 目标

**可读性**<!-- .element: class="fragment" --> **可扩展性**<!-- .element: class="fragment" --> **健壮性**<!-- .element: class="fragment" -->

+++

## 原则

S.O.L.I.D

+++

## 方式

**单元测试和重构**<!-- .element: class="fragment" --> **善用工具和库**<!-- .element: class="fragment" -->
