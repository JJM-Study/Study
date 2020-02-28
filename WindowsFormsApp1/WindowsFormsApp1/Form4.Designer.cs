namespace WindowsFormsApp1
{
    partial class Form4
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.button1 = new System.Windows.Forms.Button();
            this.label1 = new System.Windows.Forms.Label();
            this.number = new System.Windows.Forms.TextBox();
            this.years = new System.Windows.Forms.TextBox();
            this.label2 = new System.Windows.Forms.Label();
            this.semester = new System.Windows.Forms.TextBox();
            this.label3 = new System.Windows.Forms.Label();
            this.division = new System.Windows.Forms.TextBox();
            this.label4 = new System.Windows.Forms.Label();
            this.rank = new System.Windows.Forms.TextBox();
            this.label6 = new System.Windows.Forms.Label();
            this.grades = new System.Windows.Forms.TextBox();
            this.label7 = new System.Windows.Forms.Label();
            this.course = new System.Windows.Forms.TextBox();
            this.label8 = new System.Windows.Forms.Label();
            this.grade = new System.Windows.Forms.TextBox();
            this.label5 = new System.Windows.Forms.Label();
            this.button2 = new System.Windows.Forms.Button();
            this.label9 = new System.Windows.Forms.Label();
            this.dataGridView1 = new System.Windows.Forms.DataGridView();
            this.button3 = new System.Windows.Forms.Button();
            this.directoryEntry1 = new System.DirectoryServices.DirectoryEntry();
            this.comboBox1 = new System.Windows.Forms.ComboBox();
            this.textBox1 = new System.Windows.Forms.TextBox();
            this.label10 = new System.Windows.Forms.Label();
            this.button4 = new System.Windows.Forms.Button();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView1)).BeginInit();
            this.SuspendLayout();
            // 
            // button1
            // 
            this.button1.BackColor = System.Drawing.Color.Aqua;
            this.button1.Font = new System.Drawing.Font("굴림", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(129)));
            this.button1.Location = new System.Drawing.Point(612, 12);
            this.button1.Name = "button1";
            this.button1.Size = new System.Drawing.Size(146, 65);
            this.button1.TabIndex = 0;
            this.button1.Text = "등록하기";
            this.button1.UseVisualStyleBackColor = false;
            this.button1.Click += new System.EventHandler(this.button1_Click);
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(379, 15);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(29, 12);
            this.label1.TabIndex = 1;
            this.label1.Text = "순번";
            // 
            // number
            // 
            this.number.Location = new System.Drawing.Point(433, 12);
            this.number.Name = "number";
            this.number.Size = new System.Drawing.Size(100, 21);
            this.number.TabIndex = 2;
            // 
            // years
            // 
            this.years.Location = new System.Drawing.Point(433, 51);
            this.years.Name = "years";
            this.years.Size = new System.Drawing.Size(100, 21);
            this.years.TabIndex = 4;
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(379, 54);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(29, 12);
            this.label2.TabIndex = 3;
            this.label2.Text = "년도";
            // 
            // semester
            // 
            this.semester.Location = new System.Drawing.Point(433, 92);
            this.semester.Name = "semester";
            this.semester.Size = new System.Drawing.Size(100, 21);
            this.semester.TabIndex = 6;
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(379, 95);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(29, 12);
            this.label3.TabIndex = 5;
            this.label3.Text = "학기";
            // 
            // division
            // 
            this.division.Location = new System.Drawing.Point(433, 131);
            this.division.Name = "division";
            this.division.Size = new System.Drawing.Size(100, 21);
            this.division.TabIndex = 8;
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Location = new System.Drawing.Point(379, 134);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(29, 12);
            this.label4.TabIndex = 7;
            this.label4.Text = "구분";
            // 
            // rank
            // 
            this.rank.Location = new System.Drawing.Point(433, 251);
            this.rank.Name = "rank";
            this.rank.Size = new System.Drawing.Size(100, 21);
            this.rank.TabIndex = 14;
            // 
            // label6
            // 
            this.label6.AutoSize = true;
            this.label6.Location = new System.Drawing.Point(379, 254);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(29, 12);
            this.label6.TabIndex = 13;
            this.label6.Text = "등급";
            // 
            // grades
            // 
            this.grades.Location = new System.Drawing.Point(433, 210);
            this.grades.Name = "grades";
            this.grades.Size = new System.Drawing.Size(100, 21);
            this.grades.TabIndex = 12;
            // 
            // label7
            // 
            this.label7.AutoSize = true;
            this.label7.Location = new System.Drawing.Point(379, 213);
            this.label7.Name = "label7";
            this.label7.Size = new System.Drawing.Size(29, 12);
            this.label7.TabIndex = 11;
            this.label7.Text = "학점";
            // 
            // course
            // 
            this.course.Location = new System.Drawing.Point(433, 171);
            this.course.Name = "course";
            this.course.Size = new System.Drawing.Size(100, 21);
            this.course.TabIndex = 10;
            // 
            // label8
            // 
            this.label8.AutoSize = true;
            this.label8.Location = new System.Drawing.Point(379, 174);
            this.label8.Name = "label8";
            this.label8.Size = new System.Drawing.Size(53, 12);
            this.label8.TabIndex = 9;
            this.label8.Text = "교과목명";
            // 
            // grade
            // 
            this.grade.Location = new System.Drawing.Point(433, 289);
            this.grade.Name = "grade";
            this.grade.Size = new System.Drawing.Size(100, 21);
            this.grade.TabIndex = 16;
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Location = new System.Drawing.Point(379, 292);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(29, 12);
            this.label5.TabIndex = 15;
            this.label5.Text = "평점";
            // 
            // button2
            // 
            this.button2.BackColor = System.Drawing.Color.LightCoral;
            this.button2.Font = new System.Drawing.Font("굴림", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(129)));
            this.button2.Location = new System.Drawing.Point(612, 210);
            this.button2.Name = "button2";
            this.button2.Size = new System.Drawing.Size(146, 65);
            this.button2.TabIndex = 17;
            this.button2.Text = "삭제하기";
            this.button2.UseVisualStyleBackColor = false;
            this.button2.Click += new System.EventHandler(this.button2_Click);
            // 
            // label9
            // 
            this.label9.AutoSize = true;
            this.label9.Location = new System.Drawing.Point(550, 292);
            this.label9.Name = "label9";
            this.label9.Size = new System.Drawing.Size(273, 12);
            this.label9.TabIndex = 18;
            this.label9.Text = "삭제 하려면 순번만 입력 후 \'삭제하기\' 버튼 클릭!";
            // 
            // dataGridView1
            // 
            this.dataGridView1.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridView1.Location = new System.Drawing.Point(12, 41);
            this.dataGridView1.Name = "dataGridView1";
            this.dataGridView1.RowTemplate.Height = 23;
            this.dataGridView1.Size = new System.Drawing.Size(360, 275);
            this.dataGridView1.TabIndex = 19;
            this.dataGridView1.CellContentClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.dataGridView1_CellContentClick);
            // 
            // button3
            // 
            this.button3.BackColor = System.Drawing.Color.Aquamarine;
            this.button3.Font = new System.Drawing.Font("굴림", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(129)));
            this.button3.Location = new System.Drawing.Point(612, 108);
            this.button3.Name = "button3";
            this.button3.Size = new System.Drawing.Size(146, 65);
            this.button3.TabIndex = 20;
            this.button3.Text = "수정하기";
            this.button3.UseVisualStyleBackColor = false;
            this.button3.Click += new System.EventHandler(this.button3_Click);
            // 
            // comboBox1
            // 
            this.comboBox1.FormattingEnabled = true;
            this.comboBox1.Items.AddRange(new object[] {
            "전체",
            "년도-학기",
            "구분",
            "교과목명"});
            this.comboBox1.Location = new System.Drawing.Point(46, 12);
            this.comboBox1.Name = "comboBox1";
            this.comboBox1.Size = new System.Drawing.Size(121, 20);
            this.comboBox1.TabIndex = 21;
            this.comboBox1.SelectedIndexChanged += new System.EventHandler(this.comboBox1_SelectedIndexChanged);
            // 
            // textBox1
            // 
            this.textBox1.Location = new System.Drawing.Point(173, 12);
            this.textBox1.Name = "textBox1";
            this.textBox1.Size = new System.Drawing.Size(100, 21);
            this.textBox1.TabIndex = 22;
            // 
            // label10
            // 
            this.label10.AutoSize = true;
            this.label10.Location = new System.Drawing.Point(11, 15);
            this.label10.Name = "label10";
            this.label10.Size = new System.Drawing.Size(29, 12);
            this.label10.TabIndex = 23;
            this.label10.Text = "검색";
            // 
            // button4
            // 
            this.button4.Location = new System.Drawing.Point(279, 10);
            this.button4.Name = "button4";
            this.button4.Size = new System.Drawing.Size(75, 23);
            this.button4.TabIndex = 24;
            this.button4.Text = "조회";
            this.button4.UseVisualStyleBackColor = true;
            this.button4.Click += new System.EventHandler(this.button4_Click);
            // 
            // Form4
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.LightBlue;
            this.ClientSize = new System.Drawing.Size(834, 331);
            this.Controls.Add(this.button4);
            this.Controls.Add(this.label10);
            this.Controls.Add(this.textBox1);
            this.Controls.Add(this.comboBox1);
            this.Controls.Add(this.button3);
            this.Controls.Add(this.dataGridView1);
            this.Controls.Add(this.label9);
            this.Controls.Add(this.button2);
            this.Controls.Add(this.grade);
            this.Controls.Add(this.label5);
            this.Controls.Add(this.rank);
            this.Controls.Add(this.label6);
            this.Controls.Add(this.grades);
            this.Controls.Add(this.label7);
            this.Controls.Add(this.course);
            this.Controls.Add(this.label8);
            this.Controls.Add(this.division);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.semester);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.years);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.number);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.button1);
            this.Name = "Form4";
            this.Text = "성적 편집";
            this.Load += new System.EventHandler(this.Form4_Load);
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView1)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Button button1;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.TextBox number;
        private System.Windows.Forms.TextBox years;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.TextBox semester;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.TextBox division;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.TextBox rank;
        private System.Windows.Forms.Label label6;
        private System.Windows.Forms.TextBox grades;
        private System.Windows.Forms.Label label7;
        private System.Windows.Forms.TextBox course;
        private System.Windows.Forms.Label label8;
        private System.Windows.Forms.TextBox grade;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.Button button2;
        private System.Windows.Forms.Label label9;
        private System.Windows.Forms.DataGridView dataGridView1;
        private System.Windows.Forms.Button button3;
        private System.DirectoryServices.DirectoryEntry directoryEntry1;
        private System.Windows.Forms.ComboBox comboBox1;
        private System.Windows.Forms.TextBox textBox1;
        private System.Windows.Forms.Label label10;
        private System.Windows.Forms.Button button4;
    }
}