package kangwon.cse2.kdy.asynchtask;


import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**

 */
public class AsynchDemoFragment extends ListFragment {

    private static final String[] items = {"컴퓨터정보통신공학개론", "프로그래밍기초", "자바프로그래밍",
            "리눅스시스템 및 실습", "논리회로", "자료구조 및 실습 I", "C/C++프로그래밍", "웹프로그래밍",
            "전기전자공학 ", "자료구조 및 실습 II", "마이크로프로세서", "문제해결프로그래밍 ",
            "디지털시스템 설계 ", "운영체제", "컴퓨터구조", "데이터베이스", "프로그래밍언어론",
            "데이터사이언스 ", "신호처리", "앱프로그래밍", "임베디드시스템", "컴퓨터네트워크",
            "HCI", "멀티미디어공학 ", "인공지능", "영상처리", "데이터베이스 프로그래밍",
            "소프트웨어공학", "알고리즘", "컴퓨터그래픽스", "기계학습", "컴퓨터비전", "네트워크 보안",
            "정보검색", "컴퓨터시스템 보안", "영상통신", "자연어처리", "소프트웨어 구조", "패턴인식"
    };

    ArrayAdapter<String> adapter = null;
    List<String> model = new ArrayList<String>();
    AddStringTask task = null;

    public AsynchDemoFragment() {
        // Required empty public constructor
    }




    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
        adapter =
                new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,model);

        task = new AddStringTask();
        task.execute();

    }

    public void onViewCreated(View v, Bundle savedInstanceState){
        super.onViewCreated(v, savedInstanceState);

        getListView().setScrollbarFadingEnabled(false);
        setListAdapter(adapter);
    }

    public void onDestroy(){
        if(task != null){
            task.cancel(false);
        }
        super.onDestroy();
    }

    class AddStringTask extends AsyncTask<Void, String, Void> {
        protected Void doInBackground(Void... unused){
            for(String item : items){
                if(isCancelled())
                    break;

                publishProgress(item);

                try{
                    Thread.sleep(400);
                }catch (InterruptedException e){
                    e.printStackTrace();
                    break;
                }
            }
            return (null);
        }
        protected void onPostExecute(Void unused){
            Toast.makeText(getActivity(),"완료",Toast.LENGTH_SHORT).show();
            task=null;
        }

        protected void onProgressUpdate(String... item){
            if(!isCancelled()){
                adapter.add(item[0]);
            }
        }
    }
}



