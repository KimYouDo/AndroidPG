package kangwon.cse2.kdy.orientationchangelifecycle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

/*
단말 방향전환을 하면 다음과 같은 순서로 메이드가 호출됨니다.
onStop 호출됨. -> onDestroy 호출됨. -> onCreat 호출됨. -> onStart 호출됨.
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this, "onCreat 호출됨.", Toast.LENGTH_SHORT).show();
    }
    protected  void onStart(){
        super.onStart();
        Toast.makeText(this, "onStart 호출됨.", Toast.LENGTH_SHORT).show();
    }

    protected  void onStop(){
        super.onStop();
        Toast.makeText(this, "onStop 호출됨.", Toast.LENGTH_SHORT).show();
    }

    protected  void onDestroy(){
        super.onDestroy();
        Toast.makeText(this, "onDestroy 호출됨.", Toast.LENGTH_SHORT).show();
    }

}
