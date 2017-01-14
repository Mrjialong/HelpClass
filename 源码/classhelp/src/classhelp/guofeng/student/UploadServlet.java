package classhelp.guofeng.student;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.sql.Connection;
import java.sql.Statement;
import java.util.*;
import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.servlet.*;
import org.apache.commons.fileupload.disk.*;
import classhelp.guofeng.util.DBbean;

/**
 * �ļ��ϴ�
 * 
 * @author GUOFENG
 * 
 */
@WebServlet("/UploadServlet")
public class UploadServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private String filePath; // �ļ����Ŀ¼
	private String tempPath; // ��ʱ�ļ�Ŀ¼
	private String studentid;
	private String workid;
	private String filename;
	private String SQLFileName;

	// ��ʼ��
	public void init(ServletConfig config) throws ServletException {
		// super.init(config);
		// ���Դ������ļ��л�ó�ʼ������
		// filePath = config.getInitParameter("filepath");
		// tempPath = config.getInitParameter("temppath");
		// ServletContext context = getServletContext();
		// filePath = context.getRealPath(filePath);
		// tempPath = context.getRealPath(tempPath);
		filePath = "D:/ClassHelpFile/file";
		tempPath = "D:/ClassHelpFile/temp";
	}

	// doPost
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException {
		res.setContentType("text/html");
		res.setCharacterEncoding("utf-8");
		PrintWriter pw = res.getWriter();
		try {
			DiskFileItemFactory diskFactory = new DiskFileItemFactory();
			// threshold ���ޡ��ٽ�ֵ����Ӳ�̻��� 1M
			diskFactory.setSizeThreshold(10 * 1024);
			// repository �����ң�����ʱ�ļ�Ŀ¼
			diskFactory.setRepository(new File(tempPath));

			ServletFileUpload upload = new ServletFileUpload(diskFactory);
			// ��ֹ����
			upload.setHeaderEncoding("UTF-8");
			// ���������ϴ�������ļ���С
			upload.setSizeMax(10 * 1024 * 1024);
			// ����HTTP������Ϣͷ

			List<FileItem> fileItems = upload.parseRequest(req);
			Iterator<FileItem> iter = fileItems.iterator();
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();
				if (item.isFormField()) {
					processFormField(item, pw);// ���������
				} else {
					processUploadFile(item, pw);// �����ϴ����ļ�
				}
			}

			SQLFileName = filePath + "/" + filename;
			//System.out.println(workid + " & " + studentid + " & " + SQLFileName);
			// ���浽���ݿ�
			String sql = "insert into finishwork(workid,studentid,fileurl) values('"
					+ workid + "','" + studentid + "','" + SQLFileName + "')";
			Connection conn = DBbean.getConn();
			Statement stmt = DBbean.getStatement(conn);
			DBbean.getResultSetOfInsert(stmt, sql);
			DBbean.close(stmt);
			DBbean.close(conn);
			pw.print("<script>alert('" + "������ҵ��" + filename
					+ " �ϴ���ɣ�');</script>");
			pw.close();

		} catch (Exception e) {
			System.out.println("�쳣��ʹ�� fileupload �������쳣!");
			e.printStackTrace();
		}

	}

	// ���������
	private void processFormField(FileItem item, PrintWriter pw)
			throws Exception {
		String name = item.getFieldName();
		if (name.equals("stuid")) {
			studentid = item.getString();
		} else if (name.equals("workid")) {
			workid = item.getString();
		}
	}

	// �����ϴ����ļ�
	private void processUploadFile(FileItem item, PrintWriter pw)
			throws Exception {
		filename = item.getName();
		int index = filename.lastIndexOf("\\");
		filename = filename.substring(index + 1, filename.length());
		long fileSize = item.getSize();
		if ("".equals(filename) && fileSize == 0) {
			System.out.println("�ļ���Ϊ�� !");
			// return;
		}
		File uploadFile = new File(filePath + "/" + filename);
		item.write(uploadFile);
		// pw.println(filename + " �ļ�������� !");
		// pw.println("�ļ���СΪ ��" + fileSize + "\r\n");
	}

	// doGet
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException {
		doPost(req, res);
	}
}