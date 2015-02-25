
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
 
import javax.imageio.ImageIO;
import javax.naming.spi.DirStateFactory.Result;
import javax.swing.JFrame;
import javax.swing.JTextArea;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.ImageIcon;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;


public class hw2 {

	private JFrame frmSophiaWu;
	private JTextField txtX;
	private JTextField txtY;
	private JLabel lbl_map;
	private JButton btn_submit;
	private JRadioButton rbtn_emergency;
	private JTextArea query_content;
	private JCheckBox ckb_building;
	private JCheckBox ckb_student;
	private JCheckBox ckb_as;
	private JRadioButton rbtn_whole;
	private JRadioButton rbtn_point;
	private JRadioButton rbtn_range;
	private JRadioButton rbtn_surround;
	private int query_num = 0; 
	private int rt[];
	
	private int mousex = 0;
	private int mousey = 0;
	 ArrayList<Integer> pX = new ArrayList<Integer>();
	 ArrayList<Integer> pY = new ArrayList<Integer>();
	private int tryr = 0;
	boolean isFirstPoint = true;
	boolean firstclick = true;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					hw2 window = new hw2();
					window.frmSophiaWu.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public hw2() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSophiaWu = new JFrame();
		frmSophiaWu.setTitle("Sophia Wu(7988-381-741)");
		//frame.setBounds(100, 100, 1200, 800);
		frmSophiaWu.setSize(1100, 730);
		frmSophiaWu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSophiaWu.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel_map = new JPanel();
		frmSophiaWu.getContentPane().add(panel_map, BorderLayout.WEST);
		GridBagLayout gbl_panel_map = new GridBagLayout();

		gbl_panel_map.columnWidths = new int[]{820, 0};
		gbl_panel_map.rowHeights = new int[]{580, 0, 0, 0};
		gbl_panel_map.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panel_map.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_map.setLayout(gbl_panel_map);
		
		lbl_map = new JLabel("");
		
		//lbl_map.setSize(820, 580);
		
		lbl_map.addMouseListener(new MouseAdapter());
		lbl_map.setIcon(new ImageIcon(getClass().getResource("map.jpg")));
		lbl_map.addMouseMotionListener(new MouseMotion());
		//lbl_map.addMouseListener(new MouseClickListener());
		GridBagConstraints gbc_lbl_map = new GridBagConstraints();
		gbc_lbl_map.insets = new Insets(0, 0, 5, 0);
		gbc_lbl_map.fill = GridBagConstraints.BOTH;
		gbc_lbl_map.gridx = 0;
		gbc_lbl_map.gridy = 0;
		panel_map.add(lbl_map, gbc_lbl_map);
		query_content = new JTextArea();
		query_content.setEditable(false);
		query_content.setRows(5);
		JScrollPane content_scroll = new JScrollPane(query_content);
		GridBagConstraints gbc_content_scroll = new GridBagConstraints();
		gbc_content_scroll.anchor = GridBagConstraints.NORTH;
		gbc_content_scroll.fill = GridBagConstraints.HORIZONTAL;
		gbc_content_scroll.gridx = 0;
		gbc_content_scroll.gridy = 2;
		panel_map.add(content_scroll, gbc_content_scroll);
		
		JPanel panel_select = new JPanel();
		frmSophiaWu.getContentPane().add(panel_select, BorderLayout.CENTER);
		GridBagLayout gbl_panel_select = new GridBagLayout();
		gbl_panel_select.columnWidths = new int[]{30, 30, 44, 63, 0, 0, 0, 0};
		gbl_panel_select.rowHeights = new int[]{49, 27, 0, 0, 0, 39, 0, 31, 0, 32, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel_select.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_select.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_select.setLayout(gbl_panel_select);
		
		JPanel panel_current = new JPanel();
		panel_current.setBorder(new TitledBorder(null, "Current Coordinates", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_current = new GridBagConstraints();
		gbc_panel_current.gridwidth = 6;
		gbc_panel_current.gridheight = 3;
		gbc_panel_current.insets = new Insets(0, 0, 5, 0);
		gbc_panel_current.fill = GridBagConstraints.BOTH;
		gbc_panel_current.gridx = 1;
		gbc_panel_current.gridy = 1;
		panel_select.add(panel_current, gbc_panel_current);
		GridBagLayout gbl_panel_current = new GridBagLayout();
		gbl_panel_current.columnWidths = new int[]{14, 0, 0, 0, 0};
		gbl_panel_current.rowHeights = new int[]{38, 34, 0};
		gbl_panel_current.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panel_current.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panel_current.setLayout(gbl_panel_current);
		
		JLabel lblX = new JLabel("X:");
		GridBagConstraints gbc_lblX = new GridBagConstraints();
		gbc_lblX.anchor = GridBagConstraints.WEST;
		gbc_lblX.insets = new Insets(0, 0, 5, 5);
		gbc_lblX.gridx = 0;
		gbc_lblX.gridy = 0;
		panel_current.add(lblX, gbc_lblX);
		
		txtX = new JTextField();
		txtX.setEditable(false);
		GridBagConstraints gbc_txtX = new GridBagConstraints();
		gbc_txtX.gridwidth = 2;
		gbc_txtX.insets = new Insets(0, 0, 5, 5);
		gbc_txtX.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtX.gridx = 1;
		gbc_txtX.gridy = 0;
		panel_current.add(txtX, gbc_txtX);
		txtX.setColumns(10);
		
		JLabel lblY = new JLabel("Y:");
		GridBagConstraints gbc_lblY = new GridBagConstraints();
		gbc_lblY.anchor = GridBagConstraints.WEST;
		gbc_lblY.insets = new Insets(0, 0, 0, 5);
		gbc_lblY.gridx = 0;
		gbc_lblY.gridy = 1;
		panel_current.add(lblY, gbc_lblY);
		
		txtY = new JTextField();
		txtY.setEditable(false);
		txtY.setColumns(10);
		GridBagConstraints gbc_txtY = new GridBagConstraints();
		gbc_txtY.gridwidth = 2;
		gbc_txtY.insets = new Insets(0, 0, 0, 5);
		gbc_txtY.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtY.gridx = 1;
		gbc_txtY.gridy = 1;
		panel_current.add(txtY, gbc_txtY);
		
		JPanel panel_active = new JPanel();
		panel_active.setBorder(new TitledBorder(null, "Active Feature Type", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_active = new GridBagConstraints();
		gbc_panel_active.gridheight = 3;
		gbc_panel_active.gridwidth = 6;
		gbc_panel_active.insets = new Insets(0, 0, 5, 0);
		gbc_panel_active.fill = GridBagConstraints.BOTH;
		gbc_panel_active.gridx = 1;
		gbc_panel_active.gridy = 5;
		panel_select.add(panel_active, gbc_panel_active);
		GridBagLayout gbl_panel_active = new GridBagLayout();
		gbl_panel_active.columnWidths = new int[]{93, 91, 0};
		gbl_panel_active.rowHeights = new int[]{40, 31, 0};
		gbl_panel_active.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_active.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panel_active.setLayout(gbl_panel_active);
		
		ckb_building = new JCheckBox("Building");
		GridBagConstraints gbc_ckb_building = new GridBagConstraints();
		gbc_ckb_building.anchor = GridBagConstraints.WEST;
		gbc_ckb_building.insets = new Insets(0, 0, 5, 5);
		gbc_ckb_building.gridx = 0;
		gbc_ckb_building.gridy = 0;
		ckb_building.addActionListener(new ActionHandler()
		{
			public void actionPerformed(java.awt.event.ActionEvent e) {
				jcheck_reset(e);
            }
		});
		panel_active.add(ckb_building, gbc_ckb_building);
		
		ckb_student = new JCheckBox("Students");
		GridBagConstraints gbc_ckb_student = new GridBagConstraints();
		gbc_ckb_student.anchor = GridBagConstraints.WEST;
		gbc_ckb_student.insets = new Insets(0, 0, 5, 0);
		gbc_ckb_student.gridx = 1;
		gbc_ckb_student.gridy = 0;
		ckb_student.addActionListener(new ActionHandler()
		{
			public void actionPerformed(java.awt.event.ActionEvent e) {
				jcheck_reset(e);
            }
		});
		panel_active.add(ckb_student, gbc_ckb_student);
		
		ckb_as = new JCheckBox("AS");
		GridBagConstraints gbc_ckb_as = new GridBagConstraints();
		gbc_ckb_as.anchor = GridBagConstraints.WEST;
		gbc_ckb_as.insets = new Insets(0, 0, 0, 5);
		gbc_ckb_as.gridx = 0;
		gbc_ckb_as.gridy = 1;
		ckb_as.addActionListener(new ActionHandler()
		{
			public void actionPerformed(java.awt.event.ActionEvent e) {
				jcheck_reset(e);
            }
		});
		panel_active.add(ckb_as, gbc_ckb_as);
		
		JPanel panel_query = new JPanel();
		panel_query.setBorder(new TitledBorder(null, "Query", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_query = new GridBagConstraints();
		gbc_panel_query.gridheight = 7;
		gbc_panel_query.gridwidth = 6;
		gbc_panel_query.insets = new Insets(0, 0, 5, 0);
		gbc_panel_query.fill = GridBagConstraints.BOTH;
		gbc_panel_query.gridx = 1;
		gbc_panel_query.gridy = 9;
		panel_select.add(panel_query, gbc_panel_query);
		GridBagLayout gbl_panel_query = new GridBagLayout();
		gbl_panel_query.columnWidths = new int[]{0, 0};
		gbl_panel_query.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_panel_query.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panel_query.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_query.setLayout(gbl_panel_query);
		
		rbtn_whole = new JRadioButton("Whole Region");
		rbtn_whole.setSelected(true);
		GridBagConstraints gbc_rbtn_whole = new GridBagConstraints();
		gbc_rbtn_whole.anchor = GridBagConstraints.WEST;
		gbc_rbtn_whole.insets = new Insets(0, 0, 5, 0);
		gbc_rbtn_whole.gridx = 0;
		gbc_rbtn_whole.gridy = 0;
		rbtn_whole.addActionListener(new ActionHandler()
		{
			public void actionPerformed(java.awt.event.ActionEvent e) {
                jRadio_reset(e);
            }
		});
		panel_query.add(rbtn_whole, gbc_rbtn_whole);
		
		rbtn_point = new JRadioButton("Point Query");
		GridBagConstraints gbc_rbtn_point = new GridBagConstraints();
		gbc_rbtn_point.anchor = GridBagConstraints.WEST;
		gbc_rbtn_point.insets = new Insets(0, 0, 5, 0);
		gbc_rbtn_point.gridx = 0;
		gbc_rbtn_point.gridy = 1;
		rbtn_point.addActionListener(new ActionHandler()
		{
			public void actionPerformed(java.awt.event.ActionEvent e) {
                jRadio_reset(e);
            }
		});
		panel_query.add(rbtn_point, gbc_rbtn_point);
		
		rbtn_range = new JRadioButton("Range Query");
		GridBagConstraints gbc_rbtn_range = new GridBagConstraints();
		gbc_rbtn_range.anchor = GridBagConstraints.WEST;
		gbc_rbtn_range.insets = new Insets(0, 0, 5, 0);
		gbc_rbtn_range.gridx = 0;
		gbc_rbtn_range.gridy = 2;
		rbtn_range.addActionListener(new ActionHandler()
		{
			public void actionPerformed(java.awt.event.ActionEvent e) {
				jRadio_range(e);
            }
		});
		panel_query.add(rbtn_range, gbc_rbtn_range);
		
		rbtn_surround = new JRadioButton("Surrounding Student Query");
		GridBagConstraints gbc_rbtn_surround = new GridBagConstraints();
		gbc_rbtn_surround.anchor = GridBagConstraints.WEST;
		gbc_rbtn_surround.insets = new Insets(0, 0, 5, 0);
		gbc_rbtn_surround.gridx = 0;
		gbc_rbtn_surround.gridy = 3;
		
		rbtn_surround.addActionListener(new ActionHandler()
		{
			public void actionPerformed(java.awt.event.ActionEvent e) {
				jRadio_reset(e);
            }
		});
		panel_query.add(rbtn_surround, gbc_rbtn_surround);
		
		rbtn_emergency = new JRadioButton("Emergency Query");
		GridBagConstraints gbc_rbtn_emergency = new GridBagConstraints();
		gbc_rbtn_emergency.anchor = GridBagConstraints.WEST;
		gbc_rbtn_emergency.gridx = 0;
		gbc_rbtn_emergency.gridy = 4;
		rbtn_emergency.addActionListener(new ActionHandler()
		{
			public void actionPerformed(java.awt.event.ActionEvent e) {
				jRadio_reset(e);
            }
		});
		panel_query.add(rbtn_emergency, gbc_rbtn_emergency);
		ButtonGroup btngroup = new ButtonGroup();
		btngroup.add(rbtn_whole);
		btngroup.add(rbtn_point);
		btngroup.add(rbtn_range);
		btngroup.add(rbtn_surround);
		btngroup.add(rbtn_emergency);
		
		btn_submit = new JButton("Submit     Query");
		GridBagConstraints gbc_btn_submit = new GridBagConstraints();
		gbc_btn_submit.fill = GridBagConstraints.BOTH;
		gbc_btn_submit.gridwidth = 4;
		gbc_btn_submit.insets = new Insets(0, 0, 5, 5);
		gbc_btn_submit.gridx = 1;
		gbc_btn_submit.gridy = 17;
		btn_submit.addActionListener(new ActionHandler());

		//lbl_map.addMouseMotionListener(new MouseListener());
		panel_select.add(btn_submit, gbc_btn_submit);
	
	}
	
	
	public class ActionHandler implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
			if(e.getSource()==btn_submit){
				
				
				try {
					if(rbtn_whole.isSelected()){
						/*whole region selected*/
						
						if(ckb_building.isSelected() && ckb_student.isSelected() && ckb_as.isSelected()){
							whole_region("building");
							whole_region("student");
							whole_region("anns");
							}
						else if(ckb_building.isSelected() && ckb_student.isSelected()){
							whole_region("building");
							whole_region("student");
						}
						else if(ckb_building.isSelected()  && ckb_as.isSelected()){
							whole_region("building");
							whole_region("anns");
						}
						else if(ckb_student.isSelected() && ckb_as.isSelected()){
							whole_region("student");
							whole_region("anns");
						}
						else if(ckb_building.isSelected()){
							whole_region("building");
						}
						else if(ckb_student.isSelected()){
							whole_region("student");
							}
						else if(ckb_as.isSelected()){
							whole_region("anns");
							}
						
					}
					else if(rbtn_point.isSelected()){
						if(ckb_building.isSelected() && ckb_student.isSelected() && ckb_as.isSelected()){
							
							point_query(1);
							point_query(2);
							point_query(3);
							}
						else if(ckb_building.isSelected() && ckb_student.isSelected()){
							point_query(1);
							point_query(2);
						}
						else if(ckb_building.isSelected()  && ckb_as.isSelected()){
							point_query(1);
							point_query(3);
						}
						else if(ckb_student.isSelected() && ckb_as.isSelected()){
							point_query(2);
							point_query(3);
						}
						else if(ckb_building.isSelected()){
							point_query(1);
						}
						else if(ckb_student.isSelected()){
							point_query(2);
							}
						else if(ckb_as.isSelected()){
							point_query(3);
							}
					}
					else if(rbtn_range.isSelected()){
						
						if(ckb_building.isSelected() && ckb_student.isSelected() && ckb_as.isSelected()){	
							range_query(1);
							range_query(2);
							range_query(3);
							}
						else if(ckb_building.isSelected() && ckb_student.isSelected()){
							range_query(1);
							range_query(2);
						}
						else if(ckb_building.isSelected()  && ckb_as.isSelected()){
							range_query(1);
							range_query(3);
						}
						else if(ckb_student.isSelected() && ckb_as.isSelected()){
							range_query(2);
							range_query(3);
						}
						else if(ckb_building.isSelected()){
							range_query(1);
						}
						else if(ckb_student.isSelected()){
							range_query(2);
							}
						else if(ckb_as.isSelected()){
							range_query(3);
							}
					}
					else if(rbtn_surround.isSelected()){
						/*looking for surround student*/
						String point = get_cover_points(mousex, mousey, tryr);
						surround_student(point,mousex,mousey);
						
						
					}
					else if(rbtn_emergency.isSelected()){
						emergency_query();						
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
			}//btn_submit end
		}

	}
	public class MouseMotion implements MouseMotionListener {

		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
			int mvx;
			int mvy;
			mvx = e.getX();
			mvy = e.getY();
			if(mvx <= 820 && mvy <= 580){
				txtX.setText(Integer.toString(mvx));
				txtY.setText(Integer.toString(mvy));	
			}
			else{
				txtX.setText(" ");
				txtY.setText(" ");
			}
		}


	}
	public class MouseAdapter implements MouseListener{
		
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			if(firstclick){
			if(rbtn_point.isSelected()){
				mousex = e.getX();
				mousey = e.getY();
				draw_as(mousex, mousey, 5, 50 , Color.red);
				firstclick = false;
			}
			else if(rbtn_surround.isSelected()){		   
				try {
					rt = surround(e.getX(), e.getY());
					mousex = rt[0];
					mousey = rt[1];
					tryr = rt[2];
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				firstclick = false;
			}
			else if(rbtn_range.isSelected()){
				Graphics g  = lbl_map.getGraphics();
			
				/*try {
					int rt[] = surround(e.getX(), e.getY());
					mousex = rt[0];
					mousey = rt[1];
					tryr = rt[2];
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						}
				}	*/
				if(e.getButton() == MouseEvent.BUTTON1) {
                    // check if it was first point.
                    if(isFirstPoint) {
                        // reset
                        pX.clear();
                        pY.clear();
                    }
                    // add new point
                    pX.add(e.getX());
                    pY.add(e.getY());
                    if(!isFirstPoint) {
                        g.setColor(Color.red);
                        // line from last to 2nd last point
                        g.drawLine(pX.get(pX.size()-1), pY.get(pY.size()-1), pX.get(pX.size()-2), pY.get(pY.size()-2));
                    }
                    isFirstPoint = false;
                }
                // right click to end the polygon
                else if(e.getButton() == MouseEvent.BUTTON3) {
                    // we need at least 3 points - 2 sides
                    if(pX.size() >= 3) {
                        g.setColor(Color.red);
                        // line from last to first point
                        g.drawLine(pX.get(pX.size()-1), pY.get(pY.size()-1), pX.get(0), pY.get(0));
                    }
                    isFirstPoint = false;
                } 
				//firstclick = false;
		}
			else if(rbtn_emergency.isSelected()){		   
				try {
					rt = surround(e.getX(), e.getY());
					mousex = rt[0];
					mousey = rt[1];
					tryr = rt[2];
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				firstclick = false;
			}
			}
			else{
				lbl_map.repaint();
				firstclick = true;
			}
		}
		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	}
	public void whole_region(String feature) throws SQLException {
		dbaction db = new dbaction();
		
		
	  	int student_x = 0;
	  	int student_y = 0;
	  	
	  	if(feature == "building"){
	  		int vertex = 0;
	  		String query = "select b.bvert, b.bloc.SDO_ORDINATES from building b";
	  		text_box(query);
	  		db.connection();
	  		ResultSet rs = db.getResultSet(query);
	  		
	  		while(rs.next()) {
	  			Array rsarray = rs.getArray("bloc.SDO_ORDINATES");
	  			Number[] xy = (Number[])rsarray.getArray();
	  			
	  			vertex = rs.getInt("bvert");
	  			int[] x = new int[vertex];
	  			int[] y = new int[vertex];
	  			int order = 0;
	  			System.out.println("xy length: " + xy.length);
	  			
	  			for (int i = 0; i < xy.length; i++) {
	  				x[order] = xy[i].intValue();
	  				y[order] = xy[i+1].intValue();
	  				order++;
	  				i++;	  				
	  				}
	  			draw_building(x, y, vertex, Color.yellow);
	  			}
	  		
	  		rs.close();
	  		db.close();
	  	}
	  	else if (feature == "student"){
	  		String query = "select s.sloc.SDO_POINT.X, s.sloc.SDO_POINT.Y from student s";
	  		//System.out.println(query);
	  		text_box(query);
	  		db.connection();
	  		ResultSet rs = db.getResultSet(query);
	  		
	  		while(rs.next()) {
				student_x = rs.getInt("sloc.SDO_POINT.X");
				student_y = rs.getInt("sloc.SDO_POINT.Y");
				draw_student(student_x, student_y,Color.GREEN);
				
	  		}
	  		rs.close();
	  		db.close();
	  	}
	  	else if(feature == "anns"){
	  		String query = "select a.asx, a.asy,a.radius from anns a";
	  		//System.out.println(query);
	  		text_box(query);
	  		db.connection();
	  		ResultSet rs = db.getResultSet(query);
	  		int radius = 0;
	  		
	  		while(rs.next()) {
				student_x = rs.getInt("asx");
				student_y = rs.getInt("asy");
				radius = rs.getInt("radius");
				draw_as(student_x, student_y, 15 ,radius,Color.red);	
	  		}
	  		rs.close();
	  		db.close();
	  	}
		
		
	}
	public void point_query(int table_num) throws SQLException{
		dbaction db = new dbaction();
		db.connection();
		int vertex = 0 ;
		String query = "";
		
		ResultSet rs = null;
		String paint = get_cover_points(mousex, mousey, 50);
		String query_building_nn =  "SELECT b.bloc.SDO_ORDINATES,b.bvert FROM building b WHERE SDO_NN(b.bloc,SDO_GEOMETRY(2001,NULL,SDO_POINT_TYPE("+ mousex +","+ mousey+",NULL),NULL,NULL),'SDO_NUM_RES = 1') = 'TRUE'";
		String query_building_not_nn = "SELECT b.bloc.SDO_ORDINATES, b.bvert FROM building b WHERE sdo_relate(b.bloc,sdo_geometry(2003,NULL,NULL,sdo_elem_info_array(1,1003,4),sdo_ordinate_array(" + paint + ")),'mask = anyinteract')='TRUE'";
		query_building_not_nn += " AND b.bid NOT IN(SELECT b1.bid FROM building b1 WHERE SDO_NN(b1.bloc,SDO_GEOMETRY(2001,NULL,SDO_POINT_TYPE("+ mousex +","+ mousey+",NULL),NULL,NULL),'SDO_NUM_RES = 1') = 'TRUE')";
		
		String query_student_nn ="SELECT s.sloc.SDO_POINT.X ,s.sloc.SDO_POINT.Y FROM student s WHERE SDO_NN(s.sloc,SDO_GEOMETRY(2001,NULL,SDO_POINT_TYPE("+ mousex +","+ mousey+",NULL),NULL,NULL),'SDO_NUM_RES = 1') = 'TRUE'"; 
		String query_student_not_nn ="SELECT s.sloc.SDO_POINT.X ,s.sloc.SDO_POINT.Y FROM student s WHERE sdo_relate(s.sloc,sdo_geometry(2003,NULL,NULL,sdo_elem_info_array(1,1003,4),sdo_ordinate_array(" + paint + ")),'mask = anyinteract')='TRUE'";
		query_student_not_nn += " AND s.sid NOT IN(SELECT s1.sid from student s1 WHERE SDO_NN(s1.sloc,SDO_GEOMETRY(2001,NULL,SDO_POINT_TYPE("+ mousex +","+ mousey+",NULL),NULL,NULL),'SDO_NUM_RES = 1') = 'TRUE')";
		
		String query_as_nn ="SELECT a.asx ,a.asy, a.radius FROM anns a WHERE SDO_NN(a.asloc,SDO_GEOMETRY(2001,NULL,SDO_POINT_TYPE("+ mousex +","+ mousey+",NULL),NULL,NULL),'SDO_NUM_RES = 1') = 'TRUE'";
		String query_as_not_nn ="SELECT a.asx ,a.asy, a.radius FROM anns a WHERE sdo_relate(a.asloc,sdo_geometry(2003,NULL,NULL,sdo_elem_info_array(1,1003,4),sdo_ordinate_array(" + paint + ")),'mask = anyinteract')='TRUE'";
		query_as_not_nn += " AND a.asid NOT IN(SELECT a1.asid FROM anns a1 WHERE SDO_NN(a1.asloc,SDO_GEOMETRY(2001,NULL,SDO_POINT_TYPE("+ mousex +","+ mousey+",NULL),NULL,NULL),'SDO_NUM_RES = 1') = 'TRUE')";
		switch(table_num){
		case 1: //building 
			rs = db.getResultSet(query_building_nn);
			text_box(query_building_nn);
			while(rs.next()) {
	  			Array rsarray = rs.getArray("bloc.SDO_ORDINATES");
	  			Number[] xy = (Number[])rsarray.getArray();
	  			
	  			vertex = rs.getInt("bvert");
	  			int[] x = new int[vertex];
	  			int[] y = new int[vertex];
	  			int order = 0;
	  			System.out.println("xy length: " + xy.length);
	  			
	  			for (int i = 0; i < xy.length; i++) {
	  				x[order] = xy[i].intValue();
	  				y[order] = xy[i+1].intValue();
	  				order++;
	  				i++;	  				
	  				}
	  			draw_building(x, y, vertex, Color.yellow);
			}
			rs.close();
			rs = db.getResultSet(query_building_not_nn);
			text_box(query_building_not_nn);
			while(rs.next()) {
	  			Array rsarray = rs.getArray("bloc.SDO_ORDINATES");
	  			Number[] xy = (Number[])rsarray.getArray();
	  			
	  			vertex = rs.getInt("bvert");
	  			int[] x = new int[vertex];
	  			int[] y = new int[vertex];
	  			int order = 0;
	  			System.out.println("xy length: " + xy.length);
	  			
	  			for (int i = 0; i < xy.length; i++) {
	  				x[order] = xy[i].intValue();
	  				y[order] = xy[i+1].intValue();
	  				order++;
	  				i++;	  				
	  				}
	  			draw_building(x, y, vertex, Color.green);
			}
			rs.close();
			
	  			break;
		case 2: // student
			
			rs = db.getResultSet(query_student_nn);
			text_box(query_student_nn);
			while(rs.next()){
				draw_student(rs.getInt("sloc.SDO_POINT.X"),rs.getInt("sloc.SDO_POINT.Y"),Color.yellow);
			}
			rs.close();
			rs = db.getResultSet(query_student_not_nn);
			text_box(query_student_not_nn);
			while(rs.next()){
				draw_student(rs.getInt("sloc.SDO_POINT.X"),rs.getInt("sloc.SDO_POINT.Y"),Color.green);
			}
			rs.close();
			break;
		case 3: //as
			rs = db.getResultSet(query_as_nn);
			text_box(query_as_nn);
			while(rs.next()){
				draw_as(rs.getInt("asx"),rs.getInt("asy"),15,rs.getInt("radius"),Color.yellow);
			}
			rs.close();
			rs = db.getResultSet(query_as_not_nn);
			text_box(query_as_not_nn);
			while(rs.next()){
				draw_as(rs.getInt("asx"),rs.getInt("asy"),15,rs.getInt("radius"),Color.green);
			}
			rs.close();
			break;
		
		}
		System.out.println(query);
		
		db.close();

	}
	public void range_query(int table_num) throws SQLException{
		
		ResultSet rs = null;
		dbaction db = new dbaction();
		db.connection();
		String range = range_coor();
		String query = "";
		int vertex = 0;
		switch(table_num){
		case 1:
			query = "select b.bvert,b.bloc.SDO_ORDINATES from building b where sdo_anyinteract(b.bloc,sdo_geometry(2003,NULL,NULL,sdo_elem_info_array(1,1003,1),sdo_ordinate_array(" + range + ")))='TRUE'";
			System.out.println(query);
			text_box(query);
	        rs = db.getResultSet(query);
	        while(rs.next()){
	        	Array rsarray = rs.getArray("bloc.SDO_ORDINATES");
	  			Number[] xy = (Number[])rsarray.getArray();
	  			
	  			vertex = rs.getInt("bvert");
	  			int[] x = new int[vertex];
	  			int[] y = new int[vertex];
	  			int order = 0;	  			
	  			for (int i = 0; i < xy.length; i++) {
	  				x[order] = xy[i].intValue();
	  				y[order] = xy[i+1].intValue();
	  				order++;
	  				i++;	  				
	  				}
	  			draw_building(x, y, vertex, Color.yellow);	
	        }
	        
			break;
		case 2:
			 query = "select s.sloc.SDO_POINT.X ,s.sloc.SDO_POINT.Y from student s where sdo_relate(s.sloc,sdo_geometry(2003,NULL,NULL,sdo_elem_info_array(1,1003,1),sdo_ordinate_array(" + range + ")),'mask = anyinteract')='TRUE'";
			 text_box(query);
		     rs = db.getResultSet(query);
		     while(rs.next()) {
					draw_student(rs.getInt("sloc.SDO_POINT.X"), rs.getInt("sloc.SDO_POINT.Y"),Color.GREEN);
		  		}
			break;
		case 3:
			query = "select a.asx ,a.asy,a.radius from anns a where sdo_relate(a.asloc,sdo_geometry(2003,NULL,NULL,sdo_elem_info_array(1,1003,1),sdo_ordinate_array(" + range + ")),'mask = anyinteract')='TRUE'";   
			System.out.println(query);
			text_box(query);
	        rs = db.getResultSet(query);
	        while(rs.next()){
	        	draw_as(rs.getInt("asx"), rs.getInt("asy"), 15 ,rs.getInt("radius"),Color.red);	
	        }
			break;
		}
		
		db.close();
	}
	public void surround_student(String point, int x , int y) throws SQLException{
		dbaction db = new dbaction();
		db.connection();
		
		String query = "SELECT s.sloc.SDO_POINT.X ,s.sloc.SDO_POINT.Y FROM anns a, student s WHERE a.asx = " + x + " and a.asy = " + y + " AND sdo_relate(s.sloc,sdo_geometry(2003,NULL,NULL,sdo_elem_info_array(1,1003,4),sdo_ordinate_array(" + point + ")),'mask = anyinteract')='TRUE'";
		text_box(query);
		ResultSet rs = db.getResultSet(query);
		while(rs.next()){
			draw_student(rs.getInt("sloc.SDO_POINT.X"),rs.getInt("sloc.SDO_POINT.Y"),Color.GREEN);
		}
		rs.close();
		db.close();
	}
	public void emergency_query() throws SQLException{
		String point = get_cover_points(mousex, mousey, tryr);
		
		dbaction db = new dbaction();
		db.connection();
		
		int i = 0;
		
		String bass[] = new String[20];
		String broken_as_student = "SELECT a1.asid, a1.asx, a1.asy, a1.radius, c.asid, c.cx, c.cy FROM anns a1,";
		broken_as_student +="(SELECT a.asid, s.sloc.SDO_POINT.X as cx,s.sloc.SDO_POINT.Y as cy";
		broken_as_student += " FROM anns a, student s WHERE a.asx = " + mousex + " and a.asy = " + mousey + " AND ";
		broken_as_student += "sdo_relate(s.sloc,sdo_geometry(2003,NULL,NULL,sdo_elem_info_array(1,1003,4),sdo_ordinate_array(" + point + ")),'mask = anyinteract')='TRUE') c";
		broken_as_student += " WHERE SDO_NN(a1.asloc,SDO_GEOMETRY(2001,NULL,SDO_POINT_TYPE(c.cx, c.cy ,NULL),NULL,NULL),'SDO_NUM_RES = 2') = 'TRUE' AND c.asid NOT IN a1.asid";
		text_box(broken_as_student);
		System.out.println(broken_as_student);
		ResultSet rs = db.getResultSet(broken_as_student);
			
		while(rs.next()){
			
			
			Color c = color_choice(rs.getString("asid"));
			draw_student(rs.getInt("cx"),rs.getInt("cy"),c);
			draw_as(rs.getInt("asx"),rs.getInt("asy"),15,rs.getInt("radius"),c);
		}
		
		rs.close();
		db.close();
	}
	
	public Color color_choice(String asid){
		Color c = null;
		System.out.println("choose :" + asid);
		if(asid.equals("a1psa")){
			c = Color.yellow;
		}
		else if(asid.equals("a2ohe")){
			c = Color.blue;
		}
		else if(asid.equals("a3sgm")){
			c = Color.cyan;
		}
		else if(asid.equals("a4hnb")){
			
			c = Color.magenta;
		}
		else if(asid.equals("a5vhe")){
			c = Color.orange;
		}
		else if(asid.equals("a6ssc")){
			c = Color.pink;
		}
		else if(asid.equals("a7helen")){
			c = Color.green;
		}
		return c;
	}
	public void draw_building(int[] x, int[] y, int vertex, Color c){
		Graphics g = lbl_map.getGraphics();
		g.setColor(c);
		g.drawPolygon(x, y, vertex);
		g.dispose();
	}	
	public void draw_as(int x, int y, int rec, int r, Color c){
		Graphics g = lbl_map.getGraphics();
		g.setColor(c);
		g.fillRect(x-rec/2, y-rec/2, rec, rec);
		g.drawOval(x-r, y-r, r*2, r*2);
		g.dispose();
	}
	public void draw_student(int x, int y, Color c){
		
		Graphics g = lbl_map.getGraphics();
		
		g.setColor(c);
		g.fillRect(x - 5, y - 5 , 10, 10);
		g.dispose();
		
		
	}
	public int[] surround(int x, int y) throws SQLException{
		dbaction db = new dbaction();
		int rsarray[] = new int[10];
		String query = "select a.asx ,a.asy,a.radius from anns a where SDO_NN(a.asloc,SDO_GEOMETRY(2001,NULL,SDO_POINT_TYPE("+ x +","+ y +",NULL),NULL,NULL),'SDO_NUM_RES = 1') = 'TRUE'";
		text_box(query);
		db.connection();
		
		
		ResultSet rs = db.getResultSet(query);
		while(rs.next()){
			rsarray[0] = rs.getInt("asx");
			rsarray[1] = rs.getInt("asy");
			rsarray[2] = rs.getInt("radius");
			draw_as(rs.getInt("asx"),rs.getInt("asy"), 15 ,rs.getInt("radius"),Color.red);
			}
		rs.close();
		db.close();
		
		return rsarray;
		
		}
	public String range_coor() {
        String range = pX.get(0).toString() + "," + pY.get(0).toString(); // init 
        for (int i=1; i < pX.size(); i++){
            range += "," + pX.get(i).toString() + "," + pY.get(i).toString();
        }
        range += "," + pX.get(0).toString() + "," + pY.get(0).toString();
        return range;
    }
	public void text_box(String query){ //show query in text area
		query_num++;
		query_content.append("Query " + query_num + ": " + query +"\n");
	}
	public String get_cover_points(int x, int y, int r){ //count 3 point on circle
		String points = x + "," + (y + r) + "," + x + "," + (y - r) + "," + (x + r) + "," + y;
		return points;	
	}
	private void jRadio_reset(java.awt.event.ActionEvent evt) {
        
        lbl_map.repaint();

    }
	private void jRadio_range(java.awt.event.ActionEvent evt) {
		lbl_map.repaint();
		pX.clear();
		pY.clear();
		isFirstPoint = true;
	}
	private void jcheck_reset(java.awt.event.ActionEvent evt) {
		if(rbtn_whole.isSelected()){
			lbl_map.repaint();
		}
	}
}
