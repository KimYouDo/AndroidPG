package kangwon.cse2.kdy.texting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
/* append를 사용하면 카운트 값이 변경(덮어쓰기)되는 것이 아닌 추가로 써진다.
* 그리고 80자가 넘으면 afterTextChanged이 반복되어 더이상 입력히지 못하게 한다.*/
public class MainActivity extends AppCompatActivity {

    EditText inputMassage;
    TextView inputCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputMassage = (EditText)findViewById(R.id.inputMessage);
        inputCount = (TextView)findViewById(R.id.inputCount);

        Button closeButton= (Button)findViewById(R.id.closeButton);
        closeButton.setOnClickListener(new View.OnClickListener(){
            public void  onClick(View v){
                finish();
            }
        });

        Button sendButton = (Button) findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new View.OnClickListener(){
            public void  onClick(View v){
                String message = inputMassage.getText().toString();
                Toast.makeText(getApplicationContext(), "전송한 메시지\n\n"+message, Toast.LENGTH_LONG).show();
            }
        });
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence str, int i, int i1, int i2) {
byte[] bytes = null;
                try{

                    bytes= str.toString().getBytes("KSC5601");
                    int byteCount=bytes.length;
                    inputCount.setText(byteCount+" / 80바이트");
                }catch (UnsupportedEncodingException ex){
                    ex.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable strEditable) {
String str = strEditable.toString();
                try {
                    byte[] strBtes = str.getBytes("KSC5601");
                    if(strBtes.length > 80){
                        strEditable.delete(strEditable.length()-1, strEditable.length());
                    }
                }catch (UnsupportedEncodingException ex){
                    ex.printStackTrace();
                }
            }
        };
        inputMassage.addTextChangedListener(watcher);
    }
}
