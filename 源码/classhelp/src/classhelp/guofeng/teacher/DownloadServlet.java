package classhelp.guofeng.teacher;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * �ļ�����
 * @author GUOFENG
 *
 */
public class DownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String path;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//��ȡ���͵��ļ�·��
		path = request.getParameter("fileurl");
		if (path != null) {
			download(response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public void download(HttpServletResponse response) throws IOException {
		//ȥ��·����ʣ���ļ�����
		String realPath = path.substring(path.lastIndexOf("/") + 1);
		// ����������������صķ�����ȡ����Դ���Դ��ֱ�����������
		response.setHeader("content-disposition", "attachment; filename="
				+ URLEncoder.encode(realPath, "utf-8"));
		// ��ȡ��Դ�����档
		FileInputStream fis = new FileInputStream(path);
		int len = 0;
		byte[] buf = new byte[1024];
		while ((len = fis.read(buf)) != -1) {
			response.getOutputStream().write(buf, 0, len);
		}
		fis.close();
	}

}
