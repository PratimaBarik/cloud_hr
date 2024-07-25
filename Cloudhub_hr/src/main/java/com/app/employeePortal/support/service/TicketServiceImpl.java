package com.app.employeePortal.support.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.employeePortal.category.entity.TicketsType;
import com.app.employeePortal.category.repository.TicketsTypeRepository;
import com.app.employeePortal.employee.service.EmployeeService;
import com.app.employeePortal.support.entity.Product;
import com.app.employeePortal.support.entity.Ticket;
import com.app.employeePortal.support.entity.TicketProductLink;
import com.app.employeePortal.support.mapper.ProductResponseMapper;
import com.app.employeePortal.support.mapper.TicketRequestMapper;
import com.app.employeePortal.support.mapper.TicketResponseMapper;
import com.app.employeePortal.support.repository.ProductRepository;
import com.app.employeePortal.support.repository.TicketProductLinkRepository;
import com.app.employeePortal.support.repository.TicketRepository;
import com.app.employeePortal.util.Utility;

@Service
@Transactional

public class TicketServiceImpl implements TicketService {
	
	@Autowired
	TicketRepository ticketRepository;
	@Autowired
	EmployeeService employeeService;
	@Autowired
	TicketsTypeRepository ticketsTypeRepository;
	@Autowired
	TicketProductLinkRepository ticketProductLinkRepository;
	@Autowired
	ProductRepository productRepository;
	
	@Override
	public TicketResponseMapper createTicket(TicketRequestMapper ticketRequestMapper) {
		String id = null;
		if(null!=ticketRequestMapper) {
			Ticket ticket = new Ticket();
		    ticket.setCreationDate(new Date());
		    ticket.setDescription(ticketRequestMapper.getDescription());
		    ticket.setDocumentId(ticketRequestMapper.getDocumentId());
		    ticket.setEmailId(ticketRequestMapper.getEmailId());
		    ticket.setImageId(ticketRequestMapper.getImageId());
		    ticket.setLiveInd(true);
		    ticket.setMobileNumber(ticketRequestMapper.getMobileNumber());
		    ticket.setOrderId(ticketRequestMapper.getOrderId());
		    ticket.setOrgId(ticketRequestMapper.getOrgId());
		    ticket.setStartTime(ticketRequestMapper.getStartTime());
		    ticket.setTicketName(ticketRequestMapper.getTicketName());
		    ticket.setTicketTypeId(ticketRequestMapper.getTicketTypeId());
		    ticket.setUserId(ticketRequestMapper.getUserId());
		    try {
		    ticket.setStartDate(Utility.getDateFromISOString(ticketRequestMapper.getStartDate()));
		    } catch (Exception e) {
				e.printStackTrace();
			}
		    id =ticketRepository.save(ticket).getTicketId();
		    
		    List<String> productList = ticketRequestMapper.getProductIds();
		    if(null!=productList && !productList.isEmpty()) {
		    	for(String productId : productList) {
		    		TicketProductLink ticketProductLink = new TicketProductLink();
		    		
		    		ticketProductLink.setCreationDate(new Date());
		    		ticketProductLink.setLiveInd(true);
		    		ticketProductLink.setOrgId(ticketRequestMapper.getOrgId());
		    		ticketProductLink.setProductId(productId);
		    		ticketProductLink.setTicketId(id);
		    		ticketProductLink.setUserId(ticketRequestMapper.getUserId());
		    		
		    		ticketProductLinkRepository.save(ticketProductLink);
		    	}
		    }
		}
		return getTicketByTicketId(id);
	}
	


	@Override
	public TicketResponseMapper getTicketByTicketId(String ticketId) {
		
		Ticket ticket = ticketRepository.findByTicketIdAndLiveInd(ticketId,true);
		TicketResponseMapper ticketMapper =new TicketResponseMapper();

		if (null != ticket) {
				
			ticketMapper.setDescription(ticket.getDescription());
			ticketMapper.setDocumentId(ticket.getDocumentId());
			ticketMapper.setEmailId(ticket.getEmailId());
			ticketMapper.setImageId(ticket.getImageId());
			ticketMapper.setMobileNumber(ticket.getMobileNumber());
			ticketMapper.setOrderId(ticket.getOrderId());
			ticketMapper.setStartTime(ticket.getStartTime());
			ticketMapper.setTicketName(ticket.getTicketName());
			TicketsType type = ticketsTypeRepository.findByTicketTypeIdAndLiveInd(ticket.getTicketTypeId(),true);
			if(null!=type) {
				ticketMapper.setTicketTypeName(type.getTicketType());
			}
			
			 List<ProductResponseMapper> productList = new ArrayList<>();
			 List<TicketProductLink> ticketProductLinkList =ticketProductLinkRepository.findByTicketIdAndLiveInd(ticketId,true);
		
			 if (null != ticketProductLinkList && !ticketProductLinkList.isEmpty()) {
				 ticketProductLinkList.stream().map(li->{
					 ProductResponseMapper mapper = new ProductResponseMapper();
					 	
					 	Product product = productRepository.findByIdAndActive(li.getProductId(), true);
					 	if(null!=product) {
					 		mapper.setProductId(product.getId());
					 		mapper.setProductName(product.getName());
					 	}
					 	productList.add(mapper);
						 return mapper;
			 }).collect(Collectors.toList());
				 ticketMapper.setProducts(productList);
                }
		}
		
		return ticketMapper;
		}
	
	@Override
	public List<TicketResponseMapper> getTicketByUserId(String userId, int pageNo, int pageSize) {
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("creationDate").descending());
		List<TicketResponseMapper> ticketMapper =new ArrayList<>();
		Page<Ticket> ticketList = ticketRepository.findByUserIdAndLiveIndPageWise(userId,paging);
		if(null!=ticketList && !ticketList.isEmpty()) {
			ticketMapper = ticketList.stream().map(li->{
				
				TicketResponseMapper mapper = getTicketByTicketId(li.getTicketId());
				if(null!= mapper) {
					mapper.setPageCount(ticketList.getTotalPages());
					mapper.setDataCount(ticketList.getSize());
					mapper.setListCount(ticketList.getTotalElements());
				}
				
				return mapper;
			}).collect(Collectors.toList());
		}
		
		return ticketMapper;
	}



	@Override
	public String createTicketThroughWebsite(TicketRequestMapper ticketRequestMapper) {
		String id = null;
		if(null!=ticketRequestMapper) {
			Ticket ticket = new Ticket();
		    ticket.setCreationDate(new Date());
		    ticket.setDescription(ticketRequestMapper.getDescription());
		    ticket.setDocumentId(ticketRequestMapper.getDocumentId());
		    ticket.setEmailId(ticketRequestMapper.getEmailId());
		    ticket.setImageId(ticketRequestMapper.getImageId());
		    ticket.setLiveInd(true);
		    ticket.setMobileNumber(ticketRequestMapper.getMobileNumber());
		    ticket.setOrderId(ticketRequestMapper.getOrderId());
		    ticket.setOrgId(ticketRequestMapper.getOrgId());
		    ticket.setStartTime(ticketRequestMapper.getStartTime());
		    ticket.setTicketName(ticketRequestMapper.getTicketName());
		    ticket.setTicketTypeId(ticketRequestMapper.getTicketTypeId());
		    ticket.setUserId(ticketRequestMapper.getUserId());
		    try {
		    ticket.setStartDate(Utility.getDateFromISOString(ticketRequestMapper.getStartDate()));
		    } catch (Exception e) {
				e.printStackTrace();
			}
		    id =ticketRepository.save(ticket).getTicketId();
		    
		    List<String> productList = ticketRequestMapper.getProductIds();
		    if(null!=productList && !productList.isEmpty()) {
		    	for(String productId : productList) {
		    		TicketProductLink ticketProductLink = new TicketProductLink();
		    		
		    		ticketProductLink.setCreationDate(new Date());
		    		ticketProductLink.setLiveInd(true);
		    		ticketProductLink.setOrgId(ticketRequestMapper.getOrgId());
		    		ticketProductLink.setProductId(productId);
		    		ticketProductLink.setTicketId(id);
		    		ticketProductLink.setUserId(ticketRequestMapper.getUserId());
		    		
		    		ticketProductLinkRepository.save(ticketProductLink);
		    	}
		    }
		}
		return id;
	}



	@Override
	public TicketResponseMapper updateTicket(String ticketId, TicketRequestMapper ticketRequestMapper) {
		Ticket ticket = ticketRepository.findByTicketIdAndLiveInd(ticketId,true);
		//TicketResponseMapper ticketMapper =new TicketResponseMapper();

		if (null != ticket) {
			if(null!=ticketRequestMapper.getDescription()) {
				ticket.setDescription(ticketRequestMapper.getDescription());
			}
			if(null!=ticketRequestMapper.getDocumentId()) {
				ticket.setDocumentId(ticketRequestMapper.getDocumentId());
			}
			if(null!=ticketRequestMapper.getEmailId()) {
				ticket.setEmailId(ticketRequestMapper.getEmailId());
			}
			if(null!=ticketRequestMapper.getImageId()) {
				ticket.setImageId(ticketRequestMapper.getImageId());
			}
			if(null!=ticketRequestMapper.getMobileNumber()) {
				ticket.setMobileNumber(ticketRequestMapper.getMobileNumber());
			}
			if(null!=ticketRequestMapper.getOrderId()) {
				ticket.setOrderId(ticketRequestMapper.getOrderId());
			}
			if(null!=ticketRequestMapper.getStartDate()) {
				try {
				    ticket.setStartDate(Utility.getDateFromISOString(ticketRequestMapper.getStartDate()));
				    } catch (Exception e) {
						e.printStackTrace();
					}
			}
			if(0!=ticketRequestMapper.getStartTime()) {
				ticket.setStartTime(ticketRequestMapper.getStartTime());
			}
			if(null!=ticketRequestMapper.getTicketName()) {
				ticket.setTicketName(ticketRequestMapper.getTicketName());
			}
			if(null!=ticketRequestMapper.getOrgId()) {
				ticket.setOrgId(ticketRequestMapper.getOrgId());
			}
			if(null!=ticketRequestMapper.getUserId()) {
				ticket.setUserId(ticketRequestMapper.getUserId());
			}
			if(null!=ticketRequestMapper.getTicketTypeId()) {
				ticket.setTicketTypeId(ticketRequestMapper.getTicketTypeId());
			}
			
			if(null!=ticketRequestMapper.getProductIds() && !ticketRequestMapper.getProductIds().isEmpty()) {
				List<TicketProductLink> ticketProductLinkList =ticketProductLinkRepository.findByTicketIdAndLiveInd(ticketId,true);
				 if (null != ticketProductLinkList && !ticketProductLinkList.isEmpty()) {
					 for(TicketProductLink product : ticketProductLinkList) {
						 product.setLiveInd(false);
						 ticketProductLinkRepository.save(product);
					 }
				 }
				List<String> productList = ticketRequestMapper.getProductIds();
			    if(null!=productList && !productList.isEmpty()) {
			    	for(String productId : productList) {
			    		TicketProductLink ticketProductLink = new TicketProductLink();
			    		
			    		ticketProductLink.setCreationDate(new Date());
			    		ticketProductLink.setLiveInd(true);
			    		ticketProductLink.setOrgId(ticketRequestMapper.getOrgId());
			    		ticketProductLink.setProductId(productId);
			    		ticketProductLink.setTicketId(ticketId);
			    		ticketProductLink.setUserId(ticketRequestMapper.getUserId());
			    		
			    		ticketProductLinkRepository.save(ticketProductLink);
			    	}
			    }
			}
		}
		return getTicketByTicketId(ticketRepository.save(ticket).getTicketId());
	}
	

	@Override
	public void deleteTicket(String ticketId) {
		
		if(null!=ticketId) {
			Ticket ticket = ticketRepository.findByTicketIdAndLiveInd(ticketId,true);

			if (null != ticket) {
				ticket.setLiveInd(false);
				ticketRepository.save(ticket);
			}
		}
	}

	
	
}