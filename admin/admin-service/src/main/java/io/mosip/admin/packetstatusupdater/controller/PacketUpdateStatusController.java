package io.mosip.admin.packetstatusupdater.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.mosip.admin.packetstatusupdater.dto.PacketMatchedMaResponseDto;
import io.mosip.admin.packetstatusupdater.dto.PacketResumeUpdateResponseDto;
import io.mosip.admin.packetstatusupdater.dto.PacketSendToPersoResponseDto;
import io.mosip.admin.packetstatusupdater.dto.PacketStatusUpdateResponseDto;
import io.mosip.admin.packetstatusupdater.service.PacketStatusUpdateService;
import io.mosip.admin.packetstatusupdater.util.AuditUtil;
import io.mosip.admin.packetstatusupdater.util.EventEnum;
import io.mosip.kernel.core.http.ResponseFilter;
import io.mosip.kernel.core.http.ResponseWrapper;

/**
 * The Class PacketUpdateStatusController.
 * 
 * @author Srinivasan
 */
@RestController
@RequestMapping("/packetstatusupdate")
public class PacketUpdateStatusController {

	/** The packet update status service. */
	@Autowired
	private PacketStatusUpdateService packetUpdateStatusService;

	@Autowired
	private AuditUtil auditUtil;

	/**
	 * Validate packet.
	 *
	 * @param rId the r id
	 * @return the response wrapper
	 */
	@PreAuthorize("hasAnyRole(@authorizedRoles.getGetpacketstatusupdate())")
	@GetMapping
	//@PreAuthorize("hasAnyRole('ZONAL_ADMIN','GLOBAL_ADMIN')")
	@ResponseFilter
	public ResponseWrapper<PacketStatusUpdateResponseDto> validatePacket(@RequestParam(value = "rid") String rId,
			@RequestParam(value = "langCode", required = false) String langCode) {

		auditUtil.setAuditRequestDto(EventEnum.getEventEnumWithValue(EventEnum.PKT_STATUS_UPD_API_CALLED, rId),null);
		ResponseWrapper<PacketStatusUpdateResponseDto> responseWrapper = new ResponseWrapper<>();
		responseWrapper.setResponse(packetUpdateStatusService.getStatus(rId, langCode));
		auditUtil.setAuditRequestDto(EventEnum.getEventEnumWithValue(EventEnum.PKT_STATUS_UPD_SUCCESS, rId),null);
		return responseWrapper;
	}
	
	/**
	 * Validate packet.
	 *
	 * @param rId the r id
	 * @return the response wrapper
	 */
	@PreAuthorize("hasAnyRole(@authorizedRoles.getGetpacketstatusupdate())")
	@PostMapping(value = { "/resumePacket" }, consumes = { "multipart/form-data" })
	//@PreAuthorize("hasAnyRole('ZONAL_ADMIN','GLOBAL_ADMIN')")
	public ResponseWrapper<PacketResumeUpdateResponseDto> resumePacket(@RequestParam(value = "rid") String rId,
			@RequestParam(value = "langCode", required = false) String langCode) {
		auditUtil.setAuditRequestDto(EventEnum.getEventEnumWithValue(EventEnum.PKT_STATUS_UPD_API_CALLED, rId), null);
	    ResponseWrapper<PacketResumeUpdateResponseDto> responseWrapper = new ResponseWrapper<>();
	    responseWrapper.setResponse(packetUpdateStatusService.updatePacket(rId, langCode));
	    auditUtil.setAuditRequestDto(EventEnum.getEventEnumWithValue(EventEnum.PKT_STATUS_UPD_SUCCESS, rId), null);
	    return responseWrapper;
		
	}
	
	/**
	 * Validate packet.
	 *
	 * @param rId the r id
	 * @return the response wrapper
	 */
	@PreAuthorize("hasAnyRole(@authorizedRoles.getGetpacketstatusupdate())")
	@PostMapping(value = { "/sentToPerso" }, consumes = { "multipart/form-data" })
	//@PreAuthorize("hasAnyRole('ZONAL_ADMIN','GLOBAL_ADMIN')")
	public ResponseWrapper<PacketSendToPersoResponseDto> sentPacketCardToPerso(@RequestParam(value = "rid") String rId,
			@RequestParam(value = "langCode", required = false) String langCode) {
		auditUtil.setAuditRequestDto(EventEnum.getEventEnumWithValue(EventEnum.PKT_STATUS_UPD_API_CALLED, rId), null);
	    ResponseWrapper<PacketSendToPersoResponseDto> responseWrapper = new ResponseWrapper<>();
	    responseWrapper.setResponse(packetUpdateStatusService.sentPacketCardToPerso(rId, langCode));
	    auditUtil.setAuditRequestDto(EventEnum.getEventEnumWithValue(EventEnum.PKT_STATUS_UPD_SUCCESS, rId), null);
	    return responseWrapper;
		
	}
	
	/**
	 * Get MA match RID.
	 *
	 * @param rId the r id
	 * @return the response wrapper
	 */
	@PreAuthorize("hasAnyRole(@authorizedRoles.getGetpacketstatusupdate())")
	@GetMapping(value = { "/manual-verification" }, consumes = { "multipart/form-data" })
	public ResponseWrapper<PacketMatchedMaResponseDto> getMatchedPacket(@RequestParam(value = "rid") String rId,
			@RequestParam(value = "langCode", required = false) String langCode) {
		auditUtil.setAuditRequestDto(EventEnum.getEventEnumWithValue(EventEnum.PKT_STATUS_UPD_API_CALLED, rId), null);
	    ResponseWrapper<PacketMatchedMaResponseDto> responseWrapper = new ResponseWrapper<>();
	    responseWrapper.setResponse(packetUpdateStatusService.getMatchedPacket(rId, langCode));
	    auditUtil.setAuditRequestDto(EventEnum.getEventEnumWithValue(EventEnum.PKT_STATUS_UPD_SUCCESS, rId), null);
	    return responseWrapper;
		
	}
	
}
