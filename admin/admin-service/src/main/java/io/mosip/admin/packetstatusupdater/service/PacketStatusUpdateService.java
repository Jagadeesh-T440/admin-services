package io.mosip.admin.packetstatusupdater.service;

import io.mosip.admin.packetstatusupdater.dto.PacketResumeUpdateResponseDto;
import io.mosip.admin.packetstatusupdater.dto.PacketStatusUpdateResponseDto;
import io.mosip.admin.packetstatusupdater.dto.PacketSendToPersoResponseDto;
import io.mosip.admin.packetstatusupdater.dto.PacketMatchedMaResponseDto;
/**
 * The Interface PacketStatusUpdateService.
 * @author Srinivasan
 */

public interface PacketStatusUpdateService {

	/**
	 * Gets the status.
	 *
	 * @param rid
	 *            the rid
	 * @return the status
	 */
	public PacketStatusUpdateResponseDto getStatus(String rid, String langCode);
	
	public PacketResumeUpdateResponseDto updatePacket(String rid, String langCode);

	public PacketSendToPersoResponseDto sentPacketCardToPerso(String rid, String langCode);

	public PacketMatchedMaResponseDto getMatchedPacket(String rid, String langCode);
}
