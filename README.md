# SectorMenu效果演示
![image](http://upload-images.jianshu.io/upload_images/1433157-0e103b7f4b41e928.gif?imageMogr2/auto-orient/strip)
# 如何使用
* 布局
```
<com.rance.library.SectorMenuButton    
  android:id="@+id/sector_menu"    
  android:layout_width="wrap_content"    
  android:layout_height="wrap_content"    
  app:aebAnimDurationMillis="175"    
  app:aebBlurBackground="true"    
  app:aebBlurRadius="10"    
  app:aebButtonElevation="0dp"    
  app:aebButtonGapDp="60dp"    
  app:aebEndAngleDegree="359"    
  app:aebIsSelectionMode="false"    
  app:aebMainButtonRotateAnimDurationMillis="300"    
  app:aebMainButtonRotateDegree="0"    
  app:aebMainButtonSizeDp="50dp"    
  app:aebRippleEffect="true"    
  app:aebStartAngleDegree="270"    
  app:aebSubButtonSizeDp="50dp" />
```
* 初始化菜单
```
private void initSectorMenuButton() {    
  SectorMenuButton sectorMenuButton = (SectorMenuButton) findViewById(R.id.sector_menu);    
  final List<ButtonData> buttonDatas = new ArrayList<>();    
  int[] drawable = {R.mipmap.like, R.mipmap.mark, R.mipmap.search, R.mipmap.copy};    
  for (int i = 0; i < 4; i++) {        
    //最后一个参数表示padding
    ButtonData buttonData = ButtonData.buildIconButton(this, drawable[i], 0);            
    buttonData.setBackgroundColorId(this, R.color.colorAccent);        
    buttonDatas.add(buttonData);    
  }    
  sectorMenuButton.setButtonDatas(buttonDatas);    
  setListener(sectorMenuButton);
}
```
* 设置监听
```
private void setListener(final SectorMenuButton button) {    
  button.setButtonEventListener(new ButtonEventListener() {        
    @Override        
    public void onButtonClicked(int index) {            
      showToast("button" + index);        
    }        
    @Override        
    public void onExpand() {            
      showToast("onExpand");        
    }        
    @Override        
    public void onCollapse() {            
      showToast("onCollapse");        
    }    
  });
}
```
# 自定义属性说明
* aebStartAngleDegree 展开按钮的开始角度
* aebEndAngleDegree	展开按钮的结束角度
* aebMaskBackgroundColor 当菜单为开启状态时全屏的背景颜色
* aebIsSelectionMode 当子按钮被选中，主按钮设置为选定状态
* aebAnimDurationMillis	开启关闭菜单时播放动画的时间
* aebMainButtonRotateAnimDurationMillis	主按钮旋转动画的时间
* aebMainButtonRotateDegree	主按钮旋转动画的角度
* aebButtonElevation	按钮阴影效果的范围
* aebRippleEffect	当按钮单点击时是否设置水波纹效果
* aebRippleColor	主按钮点击时水波纹效果的颜色，默认为按钮颜色
* aebMainButtonSizeDp	主按钮的大小
* aebMainButtonTextSizeSp	主按钮的文字大小
* aebMainButtonTextColor 主按钮的文字颜色
* aebSubButtonSizeDp 子按钮的大小
* aebSubButtonTextSizeSp	子按钮的文字大小
* aebSubButtonTextColor	子按钮文字颜色
* aebButtonGapDp	主按钮与子按钮之间的距离

# 其它说明
因为当中用到了高斯模糊效果，代码如下
```
@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
@WorkerThread
private Bitmap getBlurBitmap(Context context, Bitmap inBitmap, float radius) {
  if (context == null || inBitmap == null) {
    throw new IllegalArgumentException("have not called setParams() before call execute()");
  }
  int width = Math.round(inBitmap.getWidth() * SCALE);
  int height = Math.round(inBitmap.getHeight() * SCALE);
  Bitmap in = Bitmap.createScaledBitmap(inBitmap, width, height, false);
  Bitmap out = Bitmap.createBitmap(in);
  RenderScript rs = RenderScript.create(context);
  ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs,Element.U8_4(rs));
  Allocation allocationIn = Allocation.createFromBitmap(rs, in);
  Allocation allocationOut = Allocation.createFromBitmap(rs, out);
  blurScript.setRadius(radius);
  blurScript.setInput(allocationIn);
  blurScript.forEach(allocationOut);
  allocationOut.copyTo(out);
  allocationIn.destroy();
  allocationOut.destroy();
  blurScript.destroy();
  rs.destroy();
  return out;
}
```
Android Studio中，support v8包不能通过添加dependencies的方式加入。从sdk的目录下手动拷贝到工程的libs下面。这个包的参考路径如下：sdk/build-tools/21.1.1/renderscript/lib/renderscript-v8.jar。
但是有个更简单的方法，只要在build.gradle中加入下面两句话即可直接使用RenderScript相关API:
```
defaultConfig { 
    ... 
    //start 
    renderscriptTargetApi 18 
    renderscriptSupportModeEnabled true 
    //end
 }
 ```
