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
    public partial class Form4 : Form
    {
        private static string strCnn;
        // 생성할 커넥션을 담을 변수를 선언합니다
        private static MySqlConnection oCnn = null; // MySqlConnection 선언과 동시에 null 값으로 초기화합니다.
        // MYSQL 데이터 어댑터를 선언합니다.
        private MySqlDataAdapter mySqlDataAdapter;

        public Form4()
        {
            InitializeComponent();
            // 접속문 선언
            strCnn = string.Format("Data Source=webdeveloper.iptime.org; port=3306; Initial catalog=jjm; User id=new_stu; Password=00xx00xx~; charset=utf8; sslMode=none");
            // 접속 시작
            try // 데이터 베이스 문제 발생을 대비하여 예외문을 설정해줍니다.
            {
                oCnn = new MySqlConnection(strCnn);
                oCnn.Open();
                // 쿼리문 작성
                mySqlDataAdapter = new MySqlDataAdapter("SELECT * FROM `grade`", oCnn);
                DataSet DS = new DataSet();
                mySqlDataAdapter.Fill(DS);
                //DataTable changes = DataSource; // countforavg 변수로 평균을 구하기 위한 조건에 맞는 과목들의 개수를 가져오기 위해 한번 더 테이블을 설정했습니다.


                dataGridView1.DataSource = DS.Tables[0];
                DS.Tables[0].Columns["number"].ColumnName = "순번";
                DS.Tables[0].Columns["years"].ColumnName = "년도";
                DS.Tables[0].Columns["semester"].ColumnName = "학기";
                DS.Tables[0].Columns["division"].ColumnName = "구분";
                DS.Tables[0].Columns["course"].ColumnName = "교과목";
                DS.Tables[0].Columns["grades"].ColumnName = "학점";
                DS.Tables[0].Columns["rank"].ColumnName = "등급";
                DS.Tables[0].Columns["grade"].ColumnName = "평점";

            }
            catch
            { }

        }


        // 아래는 데이터 베이스 등록 버튼입니다.

        private void button1_Click(object sender, EventArgs e)
        {
            try
            {
                // 커넥션 스트링을 생성합니다.
                strCnn = string.Format("Data Source=webdeveloper.iptime.org; port=3306; Initial catalog=jjm; User id=new_stu; Password=00xx00xx~; charset=utf8; sslMode=none");
                oCnn = new MySqlConnection(strCnn);
                oCnn.Open();  // 접속합니다.
                
                //쿼리문을 작성합니다.
                mySqlDataAdapter = new MySqlDataAdapter("insert into grade(number, years, semester, division, course, grades, rank, grade) values(" + number.Text + "," + years.Text + "," + semester.Text + ",'" + division.Text + "','" + course.Text + "'," + grades.Text + ",'" + rank.Text + "'," + grade.Text + ")", oCnn);
                //  바뀐 데이터 베이스를 그리드 뷰에 반영하기 위해 다시 데이터 셋을 설정합니다.
                DataSet DS = new DataSet();
                mySqlDataAdapter.Fill(DS);

                refresh(); // 데이터베이스의 갱신 시 즉각적으로 수정사항이 반영될 수 있도록 refresh 메소드를 불러옵니다. 

                MessageBox.Show("정상 등록되었습니다.");

            }
            catch (Exception ex)
            {

                MessageBox.Show(ex.Message);
            }

        }

        private void button2_Click(object sender, EventArgs e)
        {
            try
            {
          
                strCnn = string.Format("Data Source=webdeveloper.iptime.org; port=3306; Initial catalog=jjm; User id=new_stu; Password=00xx00xx~; charset=utf8; sslMode=none");
                oCnn = new MySqlConnection(strCnn);
                oCnn.Open();
         
                mySqlDataAdapter = new MySqlDataAdapter("DELETE FROM grade WHERE number = " + number.Text + "", oCnn);
                DataSet DS = new DataSet();
                mySqlDataAdapter.Fill(DS);

                refresh();

                MessageBox.Show("정상 삭제되었습니다.");
            }
            catch (Exception ex)
            {

                MessageBox.Show(ex.Message);
            }
        }

        private void dataGridView1_CellContentClick(object sender, DataGridViewCellEventArgs e)
        {
            DataTable changes = ((DataTable)dataGridView1.DataSource).GetChanges();
            if (changes != null)
            {
                MySqlCommandBuilder mcb = new MySqlCommandBuilder(mySqlDataAdapter);
                mySqlDataAdapter.UpdateCommand = mcb.GetUpdateCommand();
                mySqlDataAdapter.Update(changes);
                ((DataTable)dataGridView1.DataSource).AcceptChanges();
            }
        }

        private void button3_Click(object sender, EventArgs e)
        {
            try
            {
                strCnn = string.Format("Data Source=webdeveloper.iptime.org; port=3306; Initial catalog=jjm; User id=new_stu; Password=00xx00xx~; charset=utf8; sslMode=none");
                oCnn = new MySqlConnection(strCnn);
                oCnn.Open();
                mySqlDataAdapter = new MySqlDataAdapter("update grade set years=(" + years.Text + "),semester=(" + semester.Text + "),division=('" + division.Text + "'),course=('" + course.Text + "'),grades=(" + grades.Text + "),rank=('" + rank.Text + "'),grade=(" + grade.Text + ") WHERE number = " + number.Text + "", oCnn);
                DataSet DS = new DataSet();
                mySqlDataAdapter.Fill(DS);

                refresh();

                MessageBox.Show("정상 수정되었습니다.");

            }
            catch (Exception ex)
            {

                MessageBox.Show(ex.Message);
            }
        }
        // 데이터베이스의 변경 처리 시 리프래시를 위한 메소드입니다. 
        
        private void refresh()
        {

            DataSet DS = new DataSet();
            mySqlDataAdapter = new MySqlDataAdapter("SELECT * FROM `grade`", oCnn);

            mySqlDataAdapter.Fill(DS, "grade");
            DataTable changes = DS.Tables["grade"];
            dataGridView1.DataSource = DS.Tables[0];

            DS.Tables[0].Columns["number"].ColumnName = "순번";
            DS.Tables[0].Columns["years"].ColumnName = "년도";
            DS.Tables[0].Columns["semester"].ColumnName = "학기";
            DS.Tables[0].Columns["division"].ColumnName = "구분";
            DS.Tables[0].Columns["course"].ColumnName = "교과목";
            DS.Tables[0].Columns["grades"].ColumnName = "학점";
            DS.Tables[0].Columns["rank"].ColumnName = "등급";
            DS.Tables[0].Columns["grade"].ColumnName = "평점";

        }

        private void Form4_Load(object sender, EventArgs e)
        {
            // 헤더열에 색을 입힙니다.

            for (int i = 0; i < 8; i++)
            {
                dataGridView1.Columns[i].HeaderCell.Style.BackColor = Color.SkyBlue;
            }
        }

        private void comboBox1_SelectedIndexChanged(object sender, EventArgs e)
        {

        }

        private void button4_Click(object sender, EventArgs e)
        {
            if (comboBox1.SelectedIndex >= 0)
            {
                DataSet DS = new DataSet(); // DataBaseSearch 란 의미로, 따로 데이터 베이스의 필터를 위하여 만든 데이터 셋.

                mySqlDataAdapter.Fill(DS, "grade");
                DataTable changes = DS.Tables["grade"];

                DS.Tables[0].Columns["number"].ColumnName = "순번";
                DS.Tables[0].Columns["years"].ColumnName = "년도";
                DS.Tables[0].Columns["semester"].ColumnName = "학기";
                DS.Tables[0].Columns["division"].ColumnName = "구분";
                DS.Tables[0].Columns["course"].ColumnName = "교과목";
                DS.Tables[0].Columns["grades"].ColumnName = "학점";
                DS.Tables[0].Columns["rank"].ColumnName = "등급";
                DS.Tables[0].Columns["grade"].ColumnName = "평점";

                // 콤보박스 0번은 '전체'보기 입니다.

                if (comboBox1.SelectedIndex == 0)
                {
           
                    dataGridView1.DataSource = changes;
                }

                // 콤보박스 1번은 '년도 및 학기별'로 필터링하기 입니다.

                if (comboBox1.SelectedIndex == 1)
                {

                    if (textBox1.Text == "2014-1")
                    {
                        changes.DefaultView.RowFilter = "년도 = 2014 AND 학기 = 1";

                        dataGridView1.DataSource = changes;
                    }
                else if (textBox1.Text == "2014-2")
                    {
                        changes.DefaultView.RowFilter = "년도 = 2014 AND 학기 = 2";

                        dataGridView1.DataSource = changes;
                    }
                    else if (textBox1.Text == "2015-1")
                    {
                       
                        changes.DefaultView.RowFilter = "년도 = 2015 AND 학기 = 1";

                        dataGridView1.DataSource = changes;
                    }
                    else if (textBox1.Text == "2015-2")
                    {
                      
                        changes.DefaultView.RowFilter = "년도 = 2015 AND 학기 = 2";

                        dataGridView1.DataSource = changes;
                    }
                    else if (textBox1.Text == "2016-1")
                    {
                       
                        changes.DefaultView.RowFilter = "년도 = 2016 AND 학기 = 1";

                        dataGridView1.DataSource = changes;
                    }
                    else if (textBox1.Text == "2016-2")
                    {
                        changes.DefaultView.RowFilter = "년도 = 2016 AND 학기 = 2";

                        dataGridView1.DataSource = changes;
                    }
                    else if (textBox1.Text == "2017-1")
                    {
                        changes.DefaultView.RowFilter = "년도 = 2017 AND 학기 = 1";

                        dataGridView1.DataSource = changes;
                    }
                    else if (textBox1.Text == "2017-2")
                    {
                        changes.DefaultView.RowFilter = "년도 = 2017 AND 학기 = 2";
                        
                        dataGridView1.DataSource = changes;
                    }

                }

                // 콤보박스 2번은 '구분'별로 검색합니다. 여기서 부턴 좀 더 깔끔한 코딩을 위해 if 방식 대신 예외문을 사용하였습니다.

                if (comboBox1.SelectedIndex == 2)
                {
                    try
                    {
                        changes.DefaultView.RowFilter = $"구분='{textBox1.Text}'";
                        dataGridView1.DataSource = changes;
                    }
                    catch { MessageBox.Show("Error"); }
                }

                // 콤보박스 3번은 '교과목의 제목'을 검색합니다.

                if (comboBox1.SelectedIndex == 3)
                {
                    try
                    {
                        changes.DefaultView.RowFilter = $"교과목='{textBox1.Text}'";
                        dataGridView1.DataSource = changes;
                    }
                    catch { MessageBox.Show("Error"); }
                }
            }
            
        }

        }
    }