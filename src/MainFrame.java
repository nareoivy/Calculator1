/*
 * ����ı��ʽ���ַ�ת��ʾ������׺ת��׺�ķ���
 * �ٴ����׺���ʽ
 * ����������ַ��ͣ���sin���������ַ����͵����㷨����ʶ�� ���ҿ�1.1�汾
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
	private final String strInPanel3[]={"sin","cos","tan","����","pow","log"};
	JButton buttonInPanel1[]=new JButton[strInPanel1.length];
	JButton buttonInPanel2[]=new JButton[strInPanel2.length]; 
	JButton buttonInPanel3[]=new JButton[strInPanel3.length]; 
	private JTextField textField;
	private JButton equalButton;
	private JButton dotButton;
	
	public MainFrame(){
		int  i;
		JPanel Panel1=new JPanel(new GridLayout(4 , 4));//��4*4������panel
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
		textField.setPreferredSize(new Dimension(320,60));//����textfield�߶ȣ�HEIGHT���ķ�����
		Panel5.add(textField);
		
		Container con=getContentPane();
		con.add(Panel1);
		con.add(Panel2);
		con.add(Panel3);
		con.add(Panel4);
		con.add(Panel5);
		setTitle("��ѧ������");
		setSize(WIDTH,HEIGHT);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		ActionListener showText=new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//�ı����ȡ���������ݣ�����ʾ�ڿ���
				textField.setText(textField.getText()+ ((JButton)e.getSource()).getText());
				expression=textField.getText();
			}
		};
		//���ּ����������������������Ҫ���     (ActionListener)showText �ļ���Ч��
		for(i=0;i<strInPanel1.length;i++){
			buttonInPanel1[i].addActionListener(showText);	
		}
		//С����ҲҪ���
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
		//�Ⱥ�
		equalButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				newexpression=Trans(expression);
				result=calculate(newexpression);
				textField.setText("��׺"+expression+"  ��׺"+newexpression+"   ���"+result);	
			}
		}
		);
	}
	//��MainFrame�е�expression��׺���ʽתΪ��׺
	public String Trans(String oldExp){
		String newExp="";
		int  i=0;
		Stack<Character> s =new Stack<Character>();//char ch = �ǻ�������char���ͣ���s.peek()���ص���object����
		//test
		if(oldExp==null) 
			return "�յ�";
		while(i<oldExp.length()){
			switch(oldExp.charAt(i)){//String�и����±�ȡ�ַ��ķ���,��ע��Stack����String���͵�
			case'(':
				s.push(oldExp.charAt(i));
				i++;
				break;
			case ')':
				while((!s.empty())&&s.peek()!='('){//����'('
					newExp+=s.pop();
				}
				while(!s.empty()){
					if(s.peek()=='(')
						s.pop();//��'('��ջ��ɾ��
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
	        	if((!s.empty())&&(s.peek()!='+'&&s.peek()!='-')&&s.peek()!='('){//֪�����ȼ�С��*/��
	        	//���� if((!s.empty())&&(s.peek()=='*'||s.peek()=='/'||���ȼ����ڵ���*/��)){
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
                  newExp+='#';//������һ�����ֵ��ַ��ָ��������Լ�����ж�λ���ֵı��ʽ
                  break;
			}
		}
		while(!s.empty()&&s.peek()!='(') {
	              newExp+=s.pop();
	       }
		return newExp;
	}
	
	//���ݺ�׺���ʽ����
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
	                        s.push(c);//����浽ջ��
	                        break;

	                case'-':
		                	a=s.pop();
	                        b=s.pop();
	                        c=b-a;
	                        s.push(c);//����浽ջ��
	                        break;
	                case'*':
		                	a=s.pop();
	                        b=s.pop();
	                        c=b*a;
	                        s.push(c);//����浽ջ��
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
	                       �ַ������ֵ��㷨ʵ�֣�
	                       1.d=10*d+cNew[j]-'0';��������ʵ�ְ�����λ�����ֵļ��㣬�磺56+1
	                       2.����С�����뷨�ǣ�������С����ʱ�����Թ���d��ֵ������10�����󣬲���
	                         Ҫ��¼��С���㵽��һ����#����λ��k������ֵҪ����pow(10,k)����d���С��
	                       -----------------------------------------------------------------*/
	                        d=0;
	                        while((i<newExp.length())&&((newExp.charAt(i)<='9'&&newExp.charAt(i)>='0')||newExp.charAt(i)=='.'))
	                        {
	                                if(newExp.charAt(i)=='.')
	                                {
	                                    i++;
	                                    k=i;//��j��ֵ����
	                                    while(newExp.charAt(k)!='#')
	                                        k++;
	                                    k=k-i;//���ߵĲ�ΪС������λ��
	                                }
	                                d=10*d+newExp.charAt(i)-'0';//�ַ������ֵ�ת��
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