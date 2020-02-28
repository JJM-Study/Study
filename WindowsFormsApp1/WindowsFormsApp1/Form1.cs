using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace WindowsFormsApp1
{
    public partial class Form1 : Form
    {

        public Form1()
        {
            InitializeComponent();
        }

        private void label1_Click(object sender, EventArgs e)
        {

        }

        private void Form1_Load(object sender, EventArgs e)
        {

        }

        private void textBox1_TextChanged(object sender, EventArgs e)
        {

        }

        private void button1_Click(object sender, EventArgs e)
        {
            string temp1 = MyID.Text; // 아이디 설정입니다.
            string temp2 = MyPassWord.Text; // 패스워드 설정입니다.
            if (temp1 == "21000000" && temp2 == "910312")
            {
                MessageBox.Show("접속하였습니다.");
                Form2 form2 = new Form2();
                form2.Show();
            }
            else
            {
                MessageBox.Show("잘못된 아이디나 패스워드입니다.");
            }
        }

        private void label1_Click_1(object sender, EventArgs e)
        {

        }
    }
}