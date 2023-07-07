package com.myspring.myproject.board.controller;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.mapper.Mapper;
import org.apache.commons.io.FileUtils;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.myspring.myproject.board.service.BoardService;
import com.myspring.myproject.board.vo.BoardVO;

@Controller("boardController")
public class BoardControllerImpl implements BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private BoardVO boardVO;
	
	private static final String FILEPATH = "C:/Users/user/Documents/serverFiles/";
	
//	@Autowired 
//	private SqlSessionTemplate sqlSessiontemplate;
	
	// �������� ó�� ���� . ... ��ü ���� ->init() -> httpservlet... https://sallykim5087.tistory.com/122
//	@Override
//	//@RequestMapping(value="/board/boardForm", method = {RequestMethod.GET, RequestMethod.POST})
//	@RequestMapping(value="/Listboard")
//	public ModelAndView boardList(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		//String view = (String)request.getAttribute("view");
//		List ListPost = boardService.listPost();
//		//ModelAndView mav = new ModelAndView(view);
//		ModelAndView mav = new ModelAndView();
//		//mav.setViewName(view);
//		mav.setViewName("/board/boardForm");
//		mav.addObject("listPost", ListPost);
//		
//		return mav;
//	}
	

	
	//https://blog.naver.com/PostView.nhn?blogId=jhj9512z&logNo=222244283964&parentCategoryNo=&categoryNo=57&viewDate=&isShowPopularPosts=true&from=search
	//https://stove99.tistory.com/78 ����
	//https://chocotaste.tistory.com/116
	//https://stylishc.tistory.com/103
	//@RequestMapping(value="/Listboard")
	@RequestMapping(value="/Listboard")
	public ModelAndView pro_selectAllPostList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> param = new HashMap<String, Object>();
		ModelAndView mav = new ModelAndView();
		param.put("c_page", request.getParameter("page")); // ���� ������
		param.put("row_count", 15); // �� ������ �� �Խù� ��
		System.out.println("param" + param);
		List result1 = boardService.BoardList(param);
		int result2 = boardService.b_count();
		
		mav.setViewName("/board/boardForm");
		mav.addObject("listPost", result1);
		mav.addObject("b_count", result2); // �Խ��� ������ ����
		
		return mav;
	}
	
	// https://doublesprogramming.tistory.com/95 �Խ��� ����� ����.
	// �Խ��� ����(����)
	@Override
	@RequestMapping(value="/board/viewPost")
	public ModelAndView viewPost(@RequestParam("postNO") int postNO, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		boardVO=boardService.postview(postNO);
		List listFile = (List) boardService.listFile(postNO);
		mav.setViewName("/board/viewPost");
		mav.addObject("postView", boardVO);
		mav.addObject("listFile", listFile); // 2023/07/06 ���ϸ���Ʈ �߰�
		return mav;
		
	}
	
	//�Խ��� ����(����)
	@Override
	@RequestMapping(value="/board/posting")
	public String posting() throws Exception {
		return "/board/viewPost";
	}
	
//	//�Խ��� ���� https://kimvampa.tistory.com/164 ������.
//	@Override
//	@RequestMapping(value="/board/insertPost")
//	public String insertPost(@ModelAttribute("boardVO") BoardVO boardVO, HttpServletRequest request, HttpServletResponse response) throws Exception {
//		boardService.insertPost(boardVO);
//		//String view = "/board/boardForm";
//		String view = "/board/boardForm";
//		return view;
//	}
	
	//�Խ��� ���� https://kimvampa.tistory.com/164 ������.
//	@Override
//	@RequestMapping(value="/board/insertPost")
//	public ModelAndView insertPost(@ModelAttribute("boardVO") BoardVO boardVO, HttpServletRequest request, HttpServletResponse response) throws Exception {
//		ModelAndView mav = new ModelAndView();
//		boardService.insertPost(boardVO);
//		int result = boardVO.getPostNO();
//		System.out.println(result);
//		String cPath = request.getContextPath(); 
//		RedirectView redirectView = new RedirectView();
//		redirectView.setUrl(cPath + "/Listboard?page=1");
//		//redirectView.setExposeModelAttributes(false);
//		mav.setView(redirectView);
//		return mav;
//	}
	
	@Override
	@RequestMapping(value="/board/insertPost")
	public ModelAndView insertPost(@ModelAttribute("boardVO") BoardVO boardVO, @RequestParam("multiFile") List<MultipartFile> multiFileList, HttpServletRequest Request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		boardService.insertPost(boardVO);
		int result = boardVO.getPostNO();
		
		
		File file = new File(FILEPATH);
		if (!file.exists()) {
			file.mkdirs();
		}
		
		if (!multiFileList.isEmpty()) {

			List<Map<String, String>> fileList = new ArrayList<>();
			
			String realName;
			String saveName;
			
			// �Ķ���ͷ� ������ ���ε� �ߴ� �� ���� �Ѱ��ְ� if������ Ȯ���ϴ� ��� �غ� ��.
			
			for(int i = 0; i < multiFileList.size(); i++) {
				realName = multiFileList.get(i).getOriginalFilename();
				String ext = realName.substring(realName.lastIndexOf("."));
				saveName = UUID.randomUUID().toString() + ext;
				
				Map<String, String> map = new HashMap<>();
				map.put("realName", realName);
				map.put("saveName", saveName);
				
				fileList.add(map);
			}
			
			// ���Ͼ��ε�
			try {
				for(int i = 0; i < multiFileList.size(); i++) {
					realName = multiFileList.get(i).getOriginalFilename();
					String ext = realName.substring(realName.lastIndexOf("."));
					saveName = UUID.randomUUID().toString() + ext;
					File uploadFile = new File(FILEPATH + fileList.get(i).get("saveName"));
					multiFileList.get(i).transferTo(uploadFile);
					
					Map<String, Object> uploadMap = new HashMap<String, Object>();
					uploadMap.put("pNO", result);
					uploadMap.put("del", 0);
					uploadMap.put("gubn", "board");
					uploadMap.put("realName", realName);
					uploadMap.put("saveName", saveName);
					uploadMap.put("savePath", FILEPATH);
					boardService.fileUpload(uploadMap);
				}
				
			} catch (IllegalStateException | IOException e) {
				System.out.println("���� ���� ���ε� ����");
				
				// ���ε� ���� �� ���� ����
				
				for (int i = 0; i < multiFileList.size(); i++) {
					new File(FILEPATH + fileList.get(i).get("saveName")).delete();
				}
				
				e.printStackTrace();
			}
		}
//		while(iterator.hasNext()) {	
//			MultipartFile mFile = mRequest.getFile(iterator.next());
//			if(mFile.getSize() > 0) {
//				UUID one = UUID.randomUUID();
//				String realName = mFile.getOriginalFilename();
//				String saveName = one + "_" + mFile.getOriginalFilename();
//				
//				Map<String, Object> uploadMap = new HashMap<String, Object>();
//				uploadMap.put("pNO", result);
//				uploadMap.put("gubn", "board");
//				uploadMap.put("realName", realName);
//				uploadMap.put("saveName", saveName);
//				uploadMap.put("savePath", FILEPATH);
//				mFile.transferTo(new File(FILEPATH + saveName));
//				boardService.fileUpload(uploadMap);
//			
//			}
//		}
		
		String cPath = Request.getContextPath(); 
		RedirectView redirectView = new RedirectView();
		redirectView.setUrl(cPath + "/Listboard?page=1");
		//redirectView.setExposeModelAttributes(false);
		mav.setView(redirectView);
		return mav;
	}
	
	@Override
	@RequestMapping(value="/board/deletePost")
	public String deletePost(@RequestParam("postNO") int postNO, @ModelAttribute("boardVO") BoardVO boardVO, HttpServletRequest request, HttpServletResponse response) throws Exception {
		//ModelAndView mav = new ModelAndView();
		boardVO.setPostNO(postNO);
		boardService.deletePost(boardVO);
		//String view = "/board/boardForm";
		return "redirect:/Listboard";
	}
	
	
	// https://dion-ko.tistory.com/69 Map, Hash ����
	@Override
	@RequestMapping(value="/board/updatePost")
	public String updatePost(@RequestParam("postNO") int postNO, @ModelAttribute("boardVO") BoardVO boardVO, HttpServletRequest request, HttpServletResponse response ) throws Exception {
		boardVO.setPostNO(postNO);
		boardService.updatePost(boardVO);
		String view = "redirect:/board/viewPost?postNO=" + postNO;
		return view;
		
	}
	
	@Override
	@RequestMapping(value="/board/replyPost")
	public ModelAndView replyPost(@ModelAttribute("boardVO") BoardVO boardVO, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		boardService.replyPost(boardVO);
		int result = boardVO.getPostNO();
		System.out.println(result);
		String cPath = request.getContextPath();
		RedirectView redirectView = new RedirectView();
		redirectView.setUrl(cPath + "/board/viewPost?postNO=" + result);
		mav.setView(redirectView);
		return mav;
		
	}
	
	@Override
	@RequestMapping(value="/downloadFile")
	public void downloadFile(@RequestParam int seq, HttpServletResponse response) throws Exception {
		List<Map<String, Object>> map = boardService.downloadList(seq);
	    if(map.size()!=0) {
			String realName = (String)map.get(0).get("realName"); 
			String saveName = (String)map.get(0).get("saveName");
			byte fileByte[] = FileUtils.readFileToByteArray(new File("C:\\Users\\user\\Downloads\\devFile"+saveName));
			
		    response.setContentType("application/octet-stream");
		    response.setContentLength(fileByte.length);
		    response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(realName,"UTF-8")+"\";");
		    response.setHeader("Content-Transfer-Encoding", "binary");
		    response.getOutputStream().write(fileByte);
		     
		    response.getOutputStream().flush();
		    response.getOutputStream().close();
	    }
	}
	
//	@Override
//	@RequestMapping("fileUpload")
//	public String fileUpload(@RequestParam Map<String, Object> map, MultipartHttpServletRequest mRequest) throws IllegalStateException, IOException {
//	
//	File file = new File();	
//		
//	return "redirect:/" // seq �� �޾Ƽ� + postNO(SEQ) ������ �ϴ� ������
//	}
	
}
