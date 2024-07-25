package com.app.employeePortal.category.controller;

import static com.app.employeePortal.authentication.constants.Constants.TOKEN_PREFIX;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.app.employeePortal.authentication.config.TokenProvider;
import com.app.employeePortal.category.mapper.ItemTaskMapper;
import com.app.employeePortal.category.service.ItemTaskService;
import com.app.employeePortal.sector.mapper.SectorMapper;

@RestController
@CrossOrigin(maxAge = 3600)

public class ItemTaskController {
	@Autowired
	ItemTaskService itemTaskService;

	@Autowired
	private TokenProvider jwtTokenUtil;

	@PostMapping("/api/v1/itemTask/save")
	public ResponseEntity<?> createItemTask(@RequestBody ItemTaskMapper itemTaskMapper,
			@RequestHeader("Authorization") String authorization) {
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			if (!StringUtils.isEmpty(itemTaskMapper.getName())) {
				String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
				boolean b = itemTaskService.checkNameInItemTaskByOrgLevel(itemTaskMapper.getName(), orgId);
				if (b == true) {
					map.put("itemTaskInd", b);
					map.put("message", "ItemTask can not be created as same name already exists!!!");
					return new ResponseEntity<>(map, HttpStatus.OK);
				} else {
					itemTaskMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
					itemTaskMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));

					ItemTaskMapper paymentId = itemTaskService.saveItemTask(itemTaskMapper);

					return new ResponseEntity<>(paymentId, HttpStatus.OK);
				}

			} else {
				map.put("message", "Please Provide SectorName !!!");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/itemTask/details/{itemTaskId}")
	public ResponseEntity<?> getItemTaskBYId(@PathVariable("itemTaskId") String itemTaskId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			ItemTaskMapper itemTaskMapper = itemTaskService.getItemTaskById(itemTaskId);

			return new ResponseEntity<>(itemTaskMapper, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/itemTask/all/{orgId}")
	public ResponseEntity<?> getItemTaskMapperByOrgId(@PathVariable("orgId") String orgId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<ItemTaskMapper> itemTaskMapper = itemTaskService.getItemTaskMapperByOrgId(orgId);

			return new ResponseEntity<>(itemTaskMapper, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PutMapping("/api/v1/itemTask/update/{itemTaskId}")

	public ResponseEntity<?> updateItemTask(@PathVariable("itemTaskId") String itemTaskId,
			@RequestBody ItemTaskMapper itemTaskMapper, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {
		Map map = new HashMap<>();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			itemTaskMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			itemTaskMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			if (!StringUtils.isEmpty(itemTaskMapper.getName())) {
				String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
				boolean b = itemTaskService.checkNameInItemTaskByOrgLevel(itemTaskMapper.getName(), orgId);
				if (b == true) {
					map.put("itemTaskInd", b);
					map.put("message", "ItemTask can not be updated as same name already exists!!!");
					return new ResponseEntity<>(map, HttpStatus.OK);
				}
			}
			return new ResponseEntity<ItemTaskMapper>(itemTaskService.updateItemTask(itemTaskId, itemTaskMapper),
					HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@DeleteMapping("/api/v1/C/delete/{itemTaskId}")
	public ResponseEntity<?> deleteItemTask(@PathVariable("itemTaskId") String itemTaskId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String userId = jwtTokenUtil.getUserIdFromToken(authToken);
			itemTaskService.deleteItemTask(itemTaskId, userId);

			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/itemTask/search/{name}")
	public ResponseEntity<?> getItemTaskByName(@PathVariable("name") String name,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			List<ItemTaskMapper> mapper = itemTaskService.getItemTaskByNameByOrgLevel(name,orgId);
			if (null != mapper && !mapper.isEmpty()) {
				return new ResponseEntity<>(mapper, HttpStatus.OK);
			} else {
				map.put("message", " No Records Found !!!");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/itemTask/count/{orgId}")
	public ResponseEntity<?> getItemTaskCountByOrgId(@RequestHeader("Authorization") String authorization,
			@PathVariable("orgId") String orgId) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			return ResponseEntity.ok(itemTaskService.getItemTaskCountByOrgId(orgId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
}
