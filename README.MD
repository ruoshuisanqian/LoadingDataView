###android 加载数据布局控件

支持加载中、错误、完成、空数据四种状态页面 使用起来十分方便 

####怎么使用

        <com.tangram.loadingdataview.LoadingDataBaseLayout
            android:layout_width="match_parent"
            android:layout_height="400dp"
            android:id="@+id/loading_view">
            <ListView
                android:id="@+id/list"
                android:layout_width="match_parent"
                android:layout_height="match_parent">
            </ListView>
        </com.tangram.loadingdataview.LoadingDataBaseLayout>
        
        LoadingDataBaseLayout layout = (LoadingDataBaseLayout)findViewById(R.id.loading_view);
        layout.loading();
        layout.error();
        layout.finish();
        layout.empty();



        
        
####自定义页面        

也可以自定义各个状态页面，只需要继承 LoadingDataBaseLayout类，重写getLoadingLayout()等方法即可


####其他

这个项目中的加载动画参考了 [AVLoadingIndicatorView](https://github.com/81813780/AVLoadingIndicatorView)项目,对动画感兴趣的朋友可以去看看
