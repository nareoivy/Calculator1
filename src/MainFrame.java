/*
 * 输入的表达式用字符转表示，用中缀转后缀的方法
 * 再处理后缀表达式
 * 运算符都是字符型，顾sin、开方等字符串型的云算法不能识别 ，且看1.1版本
 * 
 * 
 * */
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

public class MainFrame extends JFrame{
	private static int WIDTH=400;
	private static int HEIGHT=500;
	private String expression="";
	private String newexpression="";
	private double result=0.0;
	private final String strInPanel1[]={"7","8","9","+","4","5","6","-","1","2","3","*",
			"(","0",")","/"};
	private final String strInPanel2[]={"Clear","Back"};
	private final String strInPanel3[]={"sin","cos","tan","开方","pow","log"};
	JButton buttonInPanel1[]=new JButton[strInPanel1.length];
	JButton buttonInPanel2[]=new JButton[strInPanel2.length]; 
	JButton buttonInPanel3[]=new JButton[strInPanel3.length]; 
	private JTextField textField;
	private JButton equalButton;
	private JButton dotButton;
	
	public MainFrame(){
		int  i;
		JPanel Panel1=new JPanel(new GridLayout(4 , 4));//以4*4填满此panel
		Panel1.setBounds(10, 220, 360, 200);
		//Panel1.setBorder((Border) new TitledBorder(new EtchedBorder(), "panel1"));
		for(i=0;i<strInPanel1.length;i++){
			buttonInPanel1[i]=new JButton(strInPanel1[i]);	
			Panel1.add(buttonInPanel1[i]);
		}
		
		JPanel Panel2=new JPanel(new GridLayout(1, 1));
		Panel2.setBounds(10, 85, 160, 40);
		//Panel2.setBorder((Border) new TitledBorder(new EtchedBorder(), "panel2"));
		for(i=0;i<strInPanel2.length;i++){
			buttonInPanel2[i]=new JButton(strInPanel2[i]);
			Panel2.add(buttonInPanel2[i]);
		}
		
		JPanel Panel3=new JPanel(new GridLayout(2, 3));
		Panel3.setBounds(180,85, 180, 125);
		//Panel3.setBorder((Border) new TitledBorder(new EtchedBorder(), "panel3"));
		for(i=0;i<strInPanel3.length;i++){
			buttonInPanel3[i]=new JButton(strInPanel3[i]);
			Panel3.add(buttonInPanel3[i]);
		}
		
		JPanel Panel4=new JPanel(new GridLayout(1, 2));
		Panel4.setBounds(10, 125, 160, 84);
		//Panel4.setBorder((Border) new TitledBorder(new EtchedBorder(), "panel4"));
		equalButton=new JButton("=");
		dotButton=new JButton(".");
		Panel4.add(equalButton);
		Panel4.add(dotButton);
		
		JPanel Panel5=new JPanel();
		Panel5.setBounds(10, 30, 300, 30);
		Panel5.setBorder((Border) new TitledBorder(new EtchedBorder(), "panel5"));
		//textField=new JTextField(32);
		//textField.setSize(40, 50);
		textField=new JTextField();
		textField.setPreferredSize(new Dimension(320,60));//调整textfield高度（HEIGHT）的方法！
		Panel5.add(textField);
		
		Container con=getContentPane();
		con.add(Panel1);
		con.add(Panel2);
		con.add(Panel3);
		con.add(Panel4);
		con.add(Panel5);
		setTitle("科学计算器");
		setSize(WIDTH,HEIGHT);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		ActionListener showText=new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//文本框获取按键的内容，并显示在框内
				textField.setText(textField.getText()+ ((JButton)e.getSource()).getText());
				expression=textField.getText();
			}
		};
		//数字键，运算符，及特殊运算域都要添加     (ActionListener)showText 的监听效果
		for(i=0;i<strInPanel1.length;i++){
			buttonInPanel1[i].addActionListener(showText);	
		}
		//小数点也要添加
		dotButton.addActionListener(showText);
		for(i=0;i<strInPanel3.length;i++){
			buttonInPanel3[i].addActionListener(showText);
		}
		//clear
		buttonInPanel2[0].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				result=0;
				expression="";
				newexpression="";
				textField.setText("");
				//textField.setText(String.valueOf(result));
			}
		});
		//Back
		buttonInPanel2[1].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				expression=expression.substring(0, expression.length()-1);
				textField.setText(expression);
			}
			
		});
		//等号
		equalButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				newexpression=Trans(expression);
				result=calculate(newexpression);
				textField.setText("中缀"+expression+"  后缀"+newexpression+"   结果"+result);	
			}
		}
		);
	}
	//将MainFrame中的expression中缀表达式转为后缀
	public String Trans(String oldExp){
		String newExp="";
		int  i=0;
		Stack<Character> s =new Stack<Character>();//char ch = 是基本类型char类型，而s.peek()返回的是object类型
		//test
		if(oldExp==null) 
			return "空的";
		while(i<oldExp.length()){
			switch(oldExp.charAt(i)){//String中根据下标取字符的方法,但注意Stack类是String类型的
			case'(':
				s.push(oldExp.charAt(i));
				i++;
				break;
			case ')':
				while((!s.empty())&&s.peek()!='('){//弹到'('
					newExp+=s.pop();
				}
				while(!s.empty()){
					if(s.peek()=='(')
						s.pop();//将'('从栈中删掉
					else break;
				}
				i++;
				break;
			 case'+':
	         case'-':
	        	 while((!s.empty())&&s.peek()!='('){
						newExp+=s.pop();
	        	 }
	        	s.push(oldExp.charAt(i));
				i++;
				break;
				
	         case'*':
	         case'/':
	        	if((!s.empty())&&(s.peek()!='+'&&s.peek()!='-')&&s.peek()!='('){//知道优先级小于*/的
	        	//或者 if((!s.empty())&&(s.peek()=='*'||s.peek()=='/'||优先级大于等于*/的)){
	        		 		newExp+=s.pop();
	        	 }
	             s.push(oldExp.charAt(i));
	             i++;
	             break;
	         case' ':
	        	 break;
	         default:
                 while((i<oldExp.length())&&((oldExp.charAt(i)<='9'&&oldExp.charAt(i)>='0')||oldExp.charAt(i)=='.')){
                	 newExp+=oldExp.charAt(i);
                	 i++;
                  }
                  newExp+='#';//把属于一个数字的字符分隔开，可以计算带有多位数字的表达式
                  break;
			}
		}
		while(!s.empty()&&s.peek()!='(') {
	              newExp+=s.pop();
	       }
		return newExp;
	}
	
	//根据后缀表达式计算
	public double calculate(String newExp){
		Stack<Double> s=new <Double>Stack();
	    int i=0,k=0;
	    double a,b,c;
	    double d;
	    while(i<newExp.length()){
	        switch(newExp.charAt(i)){
	        		case'+':
	                        a=s.pop();
	                        b=s.pop();
	                        c=b+a;
	                        s.push(c);//结果存到栈顶
	                        break;

	                case'-':
		                	a=s.pop();
	                        b=s.pop();
	                        c=b-a;
	                        s.push(c);//结果存到栈顶
	                        break;
	                case'*':
		                	a=s.pop();
	                        b=s.pop();
	                        c=b*a;
	                        s.push(c);//结果存到栈顶
	                        break;
	                case'/':
		                	a=s.pop();
	                        b=s.pop();
	                        if(a==0){
	                        	
	                        }
	                        c=b/a;
	                        s.push(c);
	                        break;
	                default:
	                       /*----------------------------------------------------------------
	                       字符到数字的算法实现：
	                       1.d=10*d+cNew[j]-'0';此语句可以实现包含多位的数字的计算，如：56+1
	                       2.对于小数，想法是：当读到小数点时，先略过，d的值继续按10倍增大，不过
	                         要记录从小数点到下一个‘#’的位数k，最后的值要除以pow(10,k)，把d变回小数
	                       -----------------------------------------------------------------*/
	                        d=0;
	                        while((i<newExp.length())&&((newExp.charAt(i)<='9'&&newExp.charAt(i)>='0')||newExp.charAt(i)=='.'))
	                        {
	                                if(newExp.charAt(i)=='.')
	                                {
	                                    i++;
	                                    k=i;//把j的值保留
	                                    while(newExp.charAt(k)!='#')
	                                        k++;
	                                    k=k-i;//两者的差为小数点后的位数
	                                }
	                                d=10*d+newExp.charAt(i)-'0';//字符到数字的转换
	                                i++;
	                        }
	                        d=d/Math.pow(10, k);
	                        k=0;
	                        s.push(d);
	                        break;

	                }
	                i++;
	    }

	    return s.peek();
	}
	
	public static void main(String args[]){
		new MainFrame();
	}
	
}