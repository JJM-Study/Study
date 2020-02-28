using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using MySql.Data;
using MySql.Data.MySqlClient;

namespace WindowsFormsApp1

{

    public partial class Form3 : Form
    {
        
        int sum = 0; // 그리드뷰에 출력된 학점의 합을 구하기 위한 변수입니다.
        double avg = 0; // 그리드뷰에 출력된 평점 평균을 구하기 위한 변수입니다.
        double sumga = 0; // 평점 평균을 계산하기 위한 합을 구하기 위한 변수입니다.
        int count = 0; // 평점 평균을 계산하기 위한 년도, 학기 조건에 맞는 과목들의 개수를 구하기 위한 변수입니다.
        // 커넥션 스트링 변수를 초기화합니다.
        private static string strCnn;
        // 생성할 커넥션을 담을 변수를 선언합니다.
        private static MySqlConnection oCnn = null; // MySqlConnection 선언과 동시에 null 값으로 초기화합니다.
        // MYSQL 데이터 어댑터를 선언합니다.
        private MySqlDataAdapter MySqlDataAdapter;

        // 데이터 그리드 뷰를 추가 및 초기화합니다.
        private void dataGridView1_CellContentClick(object sender, DataGridViewCellEventArgs e)
        {
            DataTable changes = ((DataTable)dataGridView1.DataSource).GetChanges();
            if (changes != null)
            {
                MySqlCommandBuilder mcb = new MySqlCommandBuilder(MySqlDataAdapter);
                MySqlDataAdapter.UpdateCommand = mcb.GetUpdateCommand();
                MySqlDataAdapter.Update(changes);
                ((DataTable)dataGridView1.DataSource).AcceptChanges();
            }
        }

        public Form3()
        {
            InitializeComponent();
            // 접속문 선언입니다.
            strCnn = string.Format("Data Source=webdeveloper.iptime.org; port=3306; Initial catalog=jjm; User id=new_stu; Password=00xx00xx~; charset=utf8; sslMode=none");
            
            try // 데이터 베이스 문제 발생을 대비하여 예외문을 설정해줍니다.
            {
                oCnn = new MySqlConnection(strCnn);
                oCnn.Open();
                // 쿼리문을 작성합니다.
                MySqlDataAdapter = new MySqlDataAdapter("SELECT * FROM `grade`", oCnn);
                DataSet DS = new DataSet();
                MySqlDataAdapter.Fill(DS);

                dataGridView1.DataSource = DS.Tables[0];
                DS.Tables[0].Columns["number"].ColumnName = "순번";
                DS.Tables[0].Columns["years"].ColumnName = "년도";
                DS.Tables[0].Columns["semester"].ColumnName = "학기";
                DS.Tables[0].Columns["division"].ColumnName = "구분";
                DS.Tables[0].Columns["course"].ColumnName = "교과목";
                DS.Tables[0].Columns["grades"].ColumnName = "학점";
                DS.Tables[0].Columns["rank"].ColumnName = "등급";
                DS.Tables[0].Columns["grade"].ColumnName = "평점";

                dataGridView1.EnableHeadersVisualStyles = false; // 그리드 뷰엔 비주얼 색이 우선적으로 적용되므로 셀에 사용자 지정 색을 지정하기 위하여 Talse 처리 합니다.

                for(int i = 0; i<8; i++)
                {
                    dataGridView1.Columns[i].HeaderCell.Style.BackColor = Color.SkyBlue;
                }

                int rcount = DS.Tables[0].Rows.Count; // 그리드 뷰에 나타난 행의 개수를 읽어옵니다.

                for (int i = 0; i <= rcount; i++) // for문을 통하여 모든 행을 돌며 학점의 합을 구하도록 합니다.
                {
                    try // drs의 조건에 맞지 않는 값은 null 값이 들어가기 때문에 에러가 발생합니다. 따라서 예외문을 통해 null값이 들어가는 행은 흘려줍니다. 
                    {
                        DataRow[] drs = DS.Tables[0].Select("년도 = 2014 AND 학기 = 1");
                        int drsforprint = Convert.ToInt32(drs[i]["학점"]); // 학점의 계산을 위해 DataRow 형식의 변수 drs를 int인 drsforprint로 캐스트하였습니다.
                        double drsforprintga = Convert.ToDouble(drs[i]["평점"]); // 평점 평균의 계산을 위해 DataRow 형식의 변수 drs를 int인 drsforprint로 캐스트하였습니다.
                        count = ++count;
                        
                        sum = sum + drsforprint; // int로 캐스팅된 학점의 값이 들어있는 drsforprint를 통해 합을 계산합니다.
                        sumga = sumga + drsforprintga; // double로 캐스팅한 평점의 값이 들어있는 drsforprinta를 통해 평점 평균 계산을 위한 합을 계산합니다.
                        avg = sumga / count; // 평점 평균을 구합니다.
                    }
                    catch
                    { }

                }

                gp1.Text = sum.ToString(); // 2014년 1학기의 학점의 합을 출력합니다.
                ga1.Text = avg.ToString(); // 2014년 1학기의 평점의 합을 출력합니다.

                // 다음 for문으로 넘어가기 위하여 필요 변수들을 초기화합니다.
                sum = 0;
                sumga = 0;
                count = 0;

                for (int i = 0; i <= rcount; i++)
                {
                    try
                    {
                        DataRow[] drs = DS.Tables[0].Select("년도 = 2014 AND 학기 = 2");
                        int drsforprint = Convert.ToInt32(drs[i]["학점"]);
                        double drsforprintga = Convert.ToDouble(drs[i]["평점"]);
                        count = ++count;
                        sum = sum + drsforprint;
                        sumga = sumga + drsforprintga;
                        avg = sumga / count;
                    }
                    catch
                    { }

                }

                gp2.Text = sum.ToString(); // 2014년 2학기의 학점의 합을 출력합니다.
                ga2.Text = avg.ToString(); // 2014년 2학기의 평점의 합을 출력합니다.
                sum = 0;
                sumga = 0;
                count = 0;

                for (int i = 0; i <= rcount; i++)
                {
                    try
                    {
                        DataRow[] drs = DS.Tables[0].Select("년도 = 2015 AND 학기 = 1");
                        int drsforprint = Convert.ToInt32(drs[i]["학점"]);
                        double drsforprintga = Convert.ToDouble(drs[i]["평점"]);
                        count = ++count;
                        sum = sum + drsforprint;
                        sumga = sumga + drsforprintga;
                        avg = sumga / count;
                    }
                    catch
                    { }

                }

                gp3.Text = sum.ToString();
                ga3.Text = avg.ToString();
                sum = 0;
                sumga = 0;
                count = 0;

                for (int i = 0; i <= rcount; i++)
                {
                    try
                    {
                        DataRow[] drs = DS.Tables[0].Select("년도 = 2015 AND 학기 = 2");
                        int drsforprint = Convert.ToInt32(drs[i]["학점"]);
                        double drsforprintga = Convert.ToDouble(drs[i]["평점"]);
                        count = ++count;
                        sum = sum + drsforprint;
                        sumga = sumga + drsforprintga;
                        avg = sumga / count;
                    }
                    catch
                    { }

                }

                gp4.Text = sum.ToString();
                ga4.Text = avg.ToString();
                sum = 0;
                sumga = 0;
                count = 0;

                for (int i = 0; i <= rcount; i++)
                {
                    try
                    {
                        DataRow[] drs = DS.Tables[0].Select("년도 = 2016 AND 학기 = 1");
                        int drsforprint = Convert.ToInt32(drs[i]["학점"]);
                        double drsforprintga = Convert.ToDouble(drs[i]["평점"]);
                        count = ++count;
                        sum = sum + drsforprint;
                        sumga = sumga + drsforprintga;
                        avg = sumga / count;
                    }
                    catch
                    { }

                }

                gp5.Text = sum.ToString();
                ga5.Text = avg.ToString();
                sum = 0;
                sumga = 0;
                count = 0;

                for (int i = 0; i <= rcount; i++)
                {
                    try 
                    {
                        DataRow[] drs = DS.Tables[0].Select("년도 = 2016 AND 학기 = 2");
                        int drsforprint = Convert.ToInt32(drs[i]["학점"]);
                        double drsforprintga = Convert.ToDouble(drs[i]["평점"]);
                        count = ++count;
                        sum = sum + drsforprint;
                        sumga = sumga + drsforprintga;
                        avg = sumga / count;
                    }
                    catch
                    { }

                }

                gp6.Text = sum.ToString();
                ga6.Text = avg.ToString();
                sum = 0;
                sumga = 0;
                count = 0;

                for (int i = 0; i <= rcount; i++)
                {
                    try
                    {
                        DataRow[] drs = DS.Tables[0].Select("년도 = 2017 AND 학기 = 1");
                        int drsforprint = Convert.ToInt32(drs[i]["학점"]);
                        double drsforprintga = Convert.ToDouble(drs[i]["평점"]);
                        count = ++count;
                        sum = sum + drsforprint;
                        sumga = sumga + drsforprintga;
                        avg = sumga / count;
                    }
                    catch
                    { }

                }

                gp7.Text = sum.ToString();
                ga7.Text = avg.ToString();
                sum = 0;
                sumga = 0;
                count = 0;

                for (int i = 0; i <= rcount; i++)
                {
                    try
                    {
                        DataRow[] drs = DS.Tables[0].Select("년도 = 2017 AND 학기 = 2");
                        int drsforprint = Convert.ToInt32(drs[i]["학점"]);
                        double drsforprintga = Convert.ToDouble(drs[i]["평점"]);
                        count = ++count;
                        sum = sum + drsforprint;
                        sumga = sumga + drsforprintga;
                        avg = sumga / count;
                    }
                    catch
                    { }

                }

                gp8.Text = sum.ToString();
                ga8.Text = avg.ToString();
                //count = 0;

                // 이제 for문을 통하여 전공, 교양 등의 과목별 구분의 개수를 계산합니다.

                int sum1 = 0; // '교양'의 합을 담기 위한 변수입니다
                int sum2 = 0; // '교양필수'의 합을 담기 위한 변수입니다
                int sum3 = 0; // '전공필수'의 합을 담기 위한 변수입니다
                int sum4 = 0; // '전공선택'의 합을 담기 위한 변수입니다
                int sum5 = 0; // '일반선택'의 합을 담기 위한 변수입니다

                for (int i = 0; i <= rcount; i++)
                {
                 
                   try 
                    {
                    DataRow[] drs = DS.Tables[0].Select("구분 = '교양' OR 구분 = '교양필수' OR 구분 = '전공필수' OR 구분 = '전공선택' OR 구분 = '일반선택'");
                    string div = Convert.ToString(drs[i]["구분"]); // 각 과목의 전공, 교양 등의 구분을 판별하기 위한 변수입니다.
                    int drsforprint = Convert.ToInt32(drs[i]["학점"]); // 데이터 베이스의 값을 캐스팅하여 학점의 합을 구하기 위한 변수입니다.
                        if (div == "교양")
                            sum1 = sum1 + drsforprint;
                        else if (div == "교양필수")
                            sum2 = sum2 + drsforprint;
                        else if (div == "전공필수")
                            sum3 = sum3 + drsforprint;
                        else if (div == "전공선택")
                            sum4 = sum4 + drsforprint;
                        else if (div == "일반선택")
                            sum5 = sum5 + drsforprint;
                    }
                    catch
                    { }

                }

                tgb1.Text = sum1.ToString();
                tgb2.Text = sum2.ToString();
                tgb3.Text = sum3.ToString();
                tgb4.Text = sum4.ToString();
                tgb5.Text = sum5.ToString();
                //count = 0;

            }

            catch (MySqlException ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private void Form3_Load(object sender, EventArgs e)
        {

        }

        private void button1_Click(object sender, EventArgs e)
        {

            // 데이터 베이스의 필터를 위하여 데이터 테이블을 설정합니다.

            DataSet DS = new DataSet(); 

            MySqlDataAdapter.Fill(DS, "grade");
            DataTable changes = DS.Tables["grade"];
            changes.DefaultView.RowFilter = "years = 2014 AND semester = 1";
         
            DS.Tables[0].Columns["number"].ColumnName = "순번";
            DS.Tables[0].Columns["years"].ColumnName = "년도";
            DS.Tables[0].Columns["semester"].ColumnName = "학기";
            DS.Tables[0].Columns["division"].ColumnName = "구분";
            DS.Tables[0].Columns["course"].ColumnName = "교과목";
            DS.Tables[0].Columns["grades"].ColumnName = "학점";
            DS.Tables[0].Columns["rank"].ColumnName = "등급";
            DS.Tables[0].Columns["grade"].ColumnName = "평점";
            
            dataGridView1.Columns["순번"].Visible = false; // 학기별 성적을 볼 땐 순번이 필요없으므로 숨겨줍니다.
            dataGridView1.DataSource = changes; // 필터된 그리드뷰를 출력합니다.
        }

        private void button2_Click(object sender, EventArgs e)
        {

            DataSet DS = new DataSet();

            MySqlDataAdapter.Fill(DS, "grade");
            DataTable changes = DS.Tables["grade"];
            changes.DefaultView.RowFilter = "years = 2014 AND semester = 2";
            
            DS.Tables[0].Columns["number"].ColumnName = "순번";
            DS.Tables[0].Columns["years"].ColumnName = "년도";
            DS.Tables[0].Columns["semester"].ColumnName = "학기";
            DS.Tables[0].Columns["division"].ColumnName = "구분";
            DS.Tables[0].Columns["course"].ColumnName = "교과목";
            DS.Tables[0].Columns["grades"].ColumnName = "학점";
            DS.Tables[0].Columns["rank"].ColumnName = "등급";
            DS.Tables[0].Columns["grade"].ColumnName = "평점";
            
            dataGridView1.Columns["순번"].Visible = false;
            dataGridView1.DataSource = changes;
        }

        private void button4_Click(object sender, EventArgs e)
        {
            DataSet DS = new DataSet();

            MySqlDataAdapter.Fill(DS, "grade");
            DataTable changes = DS.Tables["grade"];
            changes.DefaultView.RowFilter = "years = 2015 AND semester = 1";
            
            DS.Tables[0].Columns["number"].ColumnName = "순번";
            DS.Tables[0].Columns["years"].ColumnName = "년도";
            DS.Tables[0].Columns["semester"].ColumnName = "학기";
            DS.Tables[0].Columns["division"].ColumnName = "구분";
            DS.Tables[0].Columns["course"].ColumnName = "교과목";
            DS.Tables[0].Columns["grades"].ColumnName = "학점";
            DS.Tables[0].Columns["rank"].ColumnName = "등급";
            DS.Tables[0].Columns["grade"].ColumnName = "평점";
            
            dataGridView1.Columns["순번"].Visible = false;
            dataGridView1.DataSource = changes;
        }

        private void button3_Click(object sender, EventArgs e)
        {
            DataSet DS = new DataSet();

            MySqlDataAdapter.Fill(DS, "grade");
            DataTable changes = DS.Tables["grade"];
            changes.DefaultView.RowFilter = "years = 2015 AND semester = 2";
            
            DS.Tables[0].Columns["number"].ColumnName = "순번";
            DS.Tables[0].Columns["years"].ColumnName = "년도";
            DS.Tables[0].Columns["semester"].ColumnName = "학기";
            DS.Tables[0].Columns["division"].ColumnName = "구분";
            DS.Tables[0].Columns["course"].ColumnName = "교과목";
            DS.Tables[0].Columns["grades"].ColumnName = "학점";
            DS.Tables[0].Columns["rank"].ColumnName = "등급";
            DS.Tables[0].Columns["grade"].ColumnName = "평점";
            
            dataGridView1.Columns["순번"].Visible = false;
            dataGridView1.DataSource = changes;
        }

        private void button8_Click(object sender, EventArgs e)
        {
            DataSet DS = new DataSet();

            MySqlDataAdapter.Fill(DS, "grade");
            DataTable changes = DS.Tables["grade"];
            changes.DefaultView.RowFilter = "years = 2016 AND semester = 1";
            
            DS.Tables[0].Columns["number"].ColumnName = "순번";
            DS.Tables[0].Columns["years"].ColumnName = "년도";
            DS.Tables[0].Columns["semester"].ColumnName = "학기";
            DS.Tables[0].Columns["division"].ColumnName = "구분";
            DS.Tables[0].Columns["course"].ColumnName = "교과목";
            DS.Tables[0].Columns["grades"].ColumnName = "학점";
            DS.Tables[0].Columns["rank"].ColumnName = "등급";
            DS.Tables[0].Columns["grade"].ColumnName = "평점";
            
            dataGridView1.Columns["순번"].Visible = false;
            dataGridView1.DataSource = changes;
        }

        private void button7_Click(object sender, EventArgs e)
        {
            DataSet DS = new DataSet();

            MySqlDataAdapter.Fill(DS, "grade");
            DataTable changes = DS.Tables["grade"];
            changes.DefaultView.RowFilter = "years = 2016 AND semester = 2";
            
            DS.Tables[0].Columns["number"].ColumnName = "순번";
            DS.Tables[0].Columns["years"].ColumnName = "년도";
            DS.Tables[0].Columns["semester"].ColumnName = "학기";
            DS.Tables[0].Columns["division"].ColumnName = "구분";
            DS.Tables[0].Columns["course"].ColumnName = "교과목";
            DS.Tables[0].Columns["grades"].ColumnName = "학점";
            DS.Tables[0].Columns["rank"].ColumnName = "등급";
            DS.Tables[0].Columns["grade"].ColumnName = "평점";
            
            dataGridView1.Columns["순번"].Visible = false;
            dataGridView1.DataSource = changes;
        }

        private void button6_Click(object sender, EventArgs e)
        {
            DataSet DS = new DataSet();

            MySqlDataAdapter.Fill(DS, "grade");
            DataTable changes = DS.Tables["grade"];
            changes.DefaultView.RowFilter = "years = 2017 AND semester = 1";
            
            DS.Tables[0].Columns["number"].ColumnName = "순번";
            DS.Tables[0].Columns["years"].ColumnName = "년도";
            DS.Tables[0].Columns["semester"].ColumnName = "학기";
            DS.Tables[0].Columns["division"].ColumnName = "구분";
            DS.Tables[0].Columns["course"].ColumnName = "교과목";
            DS.Tables[0].Columns["grades"].ColumnName = "학점";
            DS.Tables[0].Columns["rank"].ColumnName = "등급";
            DS.Tables[0].Columns["grade"].ColumnName = "평점";
            
            dataGridView1.Columns["순번"].Visible = false;
            dataGridView1.DataSource = changes;
        }

        private void button5_Click(object sender, EventArgs e)
        {
            DataSet DS = new DataSet();

            MySqlDataAdapter.Fill(DS, "grade");
            DataTable changes = DS.Tables["grade"];
            changes.DefaultView.RowFilter = "years = 2017 AND semester = 2";
            
            DS.Tables[0].Columns["number"].ColumnName = "순번";
            DS.Tables[0].Columns["years"].ColumnName = "년도";
            DS.Tables[0].Columns["semester"].ColumnName = "학기";
            DS.Tables[0].Columns["division"].ColumnName = "구분";
            DS.Tables[0].Columns["course"].ColumnName = "교과목";
            DS.Tables[0].Columns["grades"].ColumnName = "학점";
            DS.Tables[0].Columns["rank"].ColumnName = "등급";
            DS.Tables[0].Columns["grade"].ColumnName = "평점";
            
            dataGridView1.Columns["순번"].Visible = false;
            dataGridView1.DataSource = changes;
        }

        // 아래는 다시 전체 학점을 보기 위한 버튼입니다.

        private void button9_Click(object sender, EventArgs e)
        {

            DataSet DS = new DataSet();

            MySqlDataAdapter.Fill(DS, "grade");
            DataTable changes = DS.Tables["grade"];
            
            DS.Tables[0].Columns["number"].ColumnName = "순번";
            DS.Tables[0].Columns["years"].ColumnName = "년도";
            DS.Tables[0].Columns["semester"].ColumnName = "학기";
            DS.Tables[0].Columns["division"].ColumnName = "구분";
            DS.Tables[0].Columns["course"].ColumnName = "교과목";
            DS.Tables[0].Columns["grades"].ColumnName = "학점";
            DS.Tables[0].Columns["rank"].ColumnName = "등급";
            DS.Tables[0].Columns["grade"].ColumnName = "평점";
            dataGridView1.Columns["순번"].Visible = true; // 다시 전체 성적을 볼 땐 순번이 필요하므로 True로 해줍니다. 
            dataGridView1.DataSource = changes;
            

        }

        // 원하는 과목을 검색할 수 있는 기능입니다.
        
        private void button10_Click(object sender, EventArgs e)
        {

            try
            {
                DataSet DS = new DataSet();

                MySqlDataAdapter.Fill(DS, "grade");
                DataTable changes = DS.Tables["grade"];

                DS.Tables[0].Columns["number"].ColumnName = "순번";
                DS.Tables[0].Columns["years"].ColumnName = "년도";
                DS.Tables[0].Columns["semester"].ColumnName = "학기";
                DS.Tables[0].Columns["division"].ColumnName = "구분";
                DS.Tables[0].Columns["course"].ColumnName = "교과목";
                DS.Tables[0].Columns["grades"].ColumnName = "학점";
                DS.Tables[0].Columns["rank"].ColumnName = "등급";
                DS.Tables[0].Columns["grade"].ColumnName = "평점";
                dataGridView1.Columns["순번"].Visible = true;
                dataGridView1.DataSource = changes;

                changes.DefaultView.RowFilter = $"교과목='{textBox1.Text}'";
                dataGridView1.DataSource = changes;
            }
             catch { MessageBox.Show("Error"); }
            }
        
        // 구분 - 교양 과목 필터입니다.
        private void d1_Click(object sender, EventArgs e)
        {
            DataSet DS = new DataSet();

            MySqlDataAdapter.Fill(DS, "grade");
            DataTable changes = DS.Tables["grade"];
            changes.DefaultView.RowFilter = "division = '교양'";

            DS.Tables[0].Columns["number"].ColumnName = "순번";
            DS.Tables[0].Columns["years"].ColumnName = "년도";
            DS.Tables[0].Columns["semester"].ColumnName = "학기";
            DS.Tables[0].Columns["division"].ColumnName = "구분";
            DS.Tables[0].Columns["course"].ColumnName = "교과목";
            DS.Tables[0].Columns["grades"].ColumnName = "학점";
            DS.Tables[0].Columns["rank"].ColumnName = "등급";
            DS.Tables[0].Columns["grade"].ColumnName = "평점";

            dataGridView1.Columns["순번"].Visible = false;
            dataGridView1.DataSource = changes;
        }

        // 구분 - 교양필수 과목 필터입니다.
        private void d2_Click(object sender, EventArgs e)
        {
            DataSet DS = new DataSet();

            MySqlDataAdapter.Fill(DS, "grade");
            DataTable changes = DS.Tables["grade"];
            changes.DefaultView.RowFilter = "division = '교양필수'";

            DS.Tables[0].Columns["number"].ColumnName = "순번";
            DS.Tables[0].Columns["years"].ColumnName = "년도";
            DS.Tables[0].Columns["semester"].ColumnName = "학기";
            DS.Tables[0].Columns["division"].ColumnName = "구분";
            DS.Tables[0].Columns["course"].ColumnName = "교과목";
            DS.Tables[0].Columns["grades"].ColumnName = "학점";
            DS.Tables[0].Columns["rank"].ColumnName = "등급";
            DS.Tables[0].Columns["grade"].ColumnName = "평점";

            dataGridView1.Columns["순번"].Visible = false;
            dataGridView1.DataSource = changes;
        }

        // 구분 - 전공필수 과목 필터입니다.
        private void d3_Click(object sender, EventArgs e)
        {
            DataSet DS = new DataSet();

            MySqlDataAdapter.Fill(DS, "grade");
            DataTable changes = DS.Tables["grade"];
            changes.DefaultView.RowFilter = "division = '전공필수'";

            DS.Tables[0].Columns["number"].ColumnName = "순번";
            DS.Tables[0].Columns["years"].ColumnName = "년도";
            DS.Tables[0].Columns["semester"].ColumnName = "학기";
            DS.Tables[0].Columns["division"].ColumnName = "구분";
            DS.Tables[0].Columns["course"].ColumnName = "교과목";
            DS.Tables[0].Columns["grades"].ColumnName = "학점";
            DS.Tables[0].Columns["rank"].ColumnName = "등급";
            DS.Tables[0].Columns["grade"].ColumnName = "평점";

            dataGridView1.Columns["순번"].Visible = false;
            dataGridView1.DataSource = changes;
        }

        // 구분 - 전공선택 과목 필터입니다.
        private void b4_Click(object sender, EventArgs e)
        {
            DataSet DS = new DataSet();

            MySqlDataAdapter.Fill(DS, "grade");
            DataTable changes = DS.Tables["grade"];
            changes.DefaultView.RowFilter = "division = '전공선택'";

            DS.Tables[0].Columns["number"].ColumnName = "순번";
            DS.Tables[0].Columns["years"].ColumnName = "년도";
            DS.Tables[0].Columns["semester"].ColumnName = "학기";
            DS.Tables[0].Columns["division"].ColumnName = "구분";
            DS.Tables[0].Columns["course"].ColumnName = "교과목";
            DS.Tables[0].Columns["grades"].ColumnName = "학점";
            DS.Tables[0].Columns["rank"].ColumnName = "등급";
            DS.Tables[0].Columns["grade"].ColumnName = "평점";

            dataGridView1.Columns["순번"].Visible = false;
            dataGridView1.DataSource = changes;
        }

        // 구분 - 일반선택 과목 필터입니다.
        private void d5_Click(object sender, EventArgs e)
        {
            DataSet DS = new DataSet();

            MySqlDataAdapter.Fill(DS, "grade");
            DataTable changes = DS.Tables["grade"];
            changes.DefaultView.RowFilter = "division = '일반선택'";

            DS.Tables[0].Columns["number"].ColumnName = "순번";
            DS.Tables[0].Columns["years"].ColumnName = "년도";
            DS.Tables[0].Columns["semester"].ColumnName = "학기";
            DS.Tables[0].Columns["division"].ColumnName = "구분";
            DS.Tables[0].Columns["course"].ColumnName = "교과목";
            DS.Tables[0].Columns["grades"].ColumnName = "학점";
            DS.Tables[0].Columns["rank"].ColumnName = "등급";
            DS.Tables[0].Columns["grade"].ColumnName = "평점";

            dataGridView1.Columns["순번"].Visible = false;
            dataGridView1.DataSource = changes;
        }
    }
    }
    

