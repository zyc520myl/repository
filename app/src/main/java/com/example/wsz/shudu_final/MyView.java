package com.example.wsz.shudu_final;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import java.util.Random;

/**
 * Created by wsz on 2019/12/18.
 */
public class MyView extends View {
    private int usedArray[][][]=new int[9][9][];     //定义三维数组 存放usedArray
    private Button b[]=new Button[9];        //记录按键

    private float width;
    private  float height;
    private Context ThisContext;
    ShuduData shuduData = new ShuduData();
    String str = shuduData.initStr();

    @Override
    public void onSizeChanged(int w,int h,int oldw,int oldh){
        this.width=w/9f;
        this.height=h/9f;
        super.onSizeChanged(w,h,oldw,oldh);
    }

    public MyView(Context context){
        super(context);
        ThisContext=context;
        getUsedArray();
    }

    public int[] calculateAllUsedTiles(int x,int y){     //计算对应x，y位置不能使用的数据，即x，y轴上或者3*3的单元格内已有的数据
        int used[]=new int[9];
        for(int i=0;i<9;i++){
            int t=getValue(x,i);
            if(t!=0){
                used[t-1]=t;
            }
        }
        for(int i=0;i<9;i++){
            int t=getValue(i,y);
            if(t!=0){
                used[t-1]=t;
            }
        }
        int StartX=(x/3)*3;
        int StartY=(y/3)*3;
        for(int i=StartX;i<StartX+3;i++){
            for(int j=StartY;j<StartY+3;j++){
                int t=getValue(i,j);
                if(t!=0){
                    used[t-1]=t;
                }
            }
        }
        return used;
    }

    public void getUsedArray(){            //得到已使用的数据
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                usedArray[i][j]=calculateAllUsedTiles(i,j);
            }
        }
    }

    public void reDraw(){
        getUsedArray();
        invalidate();
    }

    public int getValue(int x,int y){         //将STR转化为整数形式
       return str.charAt(9*y+x)-'0';
   }



    public int[] getUsedToString(int x,int y){
        return usedArray[x][y];
    }

    public void setNewStringInsteadOfZero(int x,int y,char t){     //更新STR  填入数字后
        int index=y*9+x;
        String reStr=str.substring(0,index);
        reStr+=t;
        reStr+=str.substring(index+1,str.length());
        str=reStr;
    }

    public void onDraw(Canvas canvas){
        Paint BackgroundPaint=new Paint();
        BackgroundPaint.setARGB(255, 230, 230, 255);//FFE6E6FF
        canvas.drawRect(0,0,getWidth(),getHeight(),BackgroundPaint);

        Paint HillitePaint=new Paint();
        HillitePaint.setARGB(255, 255, 255, 255);

        Paint LinePaint=new Paint();
        LinePaint.setARGB(64, 198, 212, 239);

        Paint DeepLinePaint=new Paint();
        DeepLinePaint.setARGB(255, 56, 64, 143);

        for (int i=0;i<9;i++){
            canvas.drawLine(0,i*height,getWidth(),i*height,LinePaint);
            canvas.drawLine(0,i*height+1,getWidth(),i*height+1,HillitePaint);
            canvas.drawLine(i*width,0,i*width,getHeight(),LinePaint);
            canvas.drawLine(i*width+1,0,i*width+1,getHeight(),HillitePaint);
        }
        for(int j=0;j<3;j++){
            canvas.drawLine(0,3*j*height,getWidth(),3*j*height,DeepLinePaint);
            canvas.drawLine(0,3*j*height+1,getWidth(),3*j*height+1,HillitePaint);
            canvas.drawLine(3*j*width,0,3*j*width,getHeight(),DeepLinePaint);
            canvas.drawLine(3*j*width+1,0,3*j*width+1,getHeight(),HillitePaint);
        }
        Paint NumberPaint=new Paint();
        NumberPaint.setColor(Color.BLACK);
        NumberPaint.setTextSize((float) (height * 0.75));
        NumberPaint.setStyle(Paint.Style.STROKE);
        NumberPaint.setTextAlign(Paint.Align.CENTER);
        Paint.FontMetrics fondMetrics=NumberPaint.getFontMetrics();
        float x=width/2;
        float y=height/2-(fondMetrics.ascent+fondMetrics.descent)/2;

        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                int t= getValue(i,j);
                if(t!=0){
                    canvas.drawText(String.valueOf(t),i*width+x,j*height+y,NumberPaint);
                }
            }
        }
        super.onDraw(canvas);
    }

    public boolean onTouchEvent(MotionEvent event){
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            final int SelectedX=(int)(event.getX()/width);
            final int SelectedY=(int)(event.getY()/height);
            if(getValue(SelectedX,SelectedY)==0){
                int used[]=getUsedToString(SelectedX,SelectedY);
                final AlertDialog.Builder builder=new AlertDialog.Builder(ThisContext);
                final AlertDialog dialog=builder.create();
                View item= LayoutInflater.from(ThisContext).inflate(R.layout.button,null);
                findAllButton(item);
                for(int i=0;i<9;i++){
                    if(used[i]!=0){
                        b[i].setVisibility(View.INVISIBLE);
                    }
                }
                for(int i=0;i<9;i++){
                    final char t = (char) (i + 1 + '0');  //化为整形
                    b[i].setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            setNewStringInsteadOfZero(SelectedX,SelectedY,t);
                            reDraw();
                            dialog.dismiss();
                        }
                    });
                }
                dialog.setView(item);
                dialog.setTitle("请输入数字");
                dialog.setCancelable(true);
                dialog.show();
            }
        }
        return super.onTouchEvent(event);
    }

    public void findAllButton(View view){
        b[0] = (Button) view.findViewById(R.id.keypad_1);       //(Button) view.必须有是什么原因？
        b[1] = (Button) view.findViewById(R.id.keypad_2);
        b[2] = (Button) view.findViewById(R.id.keypad_3);
        b[3] = (Button) view.findViewById(R.id.keypad_4);
        b[4] = (Button) view.findViewById(R.id.keypad_5);
        b[5] = (Button) view.findViewById(R.id.keypad_6);
        b[6] = (Button) view.findViewById(R.id.keypad_7);
        b[7] = (Button) view.findViewById(R.id.keypad_8);
        b[8] = (Button) view.findViewById(R.id.keypad_9);
    }
}

