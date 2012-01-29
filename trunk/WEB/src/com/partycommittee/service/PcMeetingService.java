package com.partycommittee.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.partycommittee.persistence.daoimpl.PcAgencyRelationDaoImpl;
import com.partycommittee.persistence.daoimpl.PcMeetingAsenceDaoImpl;
import com.partycommittee.persistence.daoimpl.PcMeetingContentDaoImpl;
import com.partycommittee.persistence.daoimpl.PcMeetingDaoImpl;
import com.partycommittee.persistence.po.PcAgencyRelation;
import com.partycommittee.persistence.po.PcMeeting;
import com.partycommittee.persistence.po.PcMeetingContent;
import com.partycommittee.remote.vo.PcMeetingContentVo;
import com.partycommittee.remote.vo.PcMeetingVo;

@Transactional
@Service("PcMeetingService")
public class PcMeetingService {

	@Resource(name="PcMeetingDaoImpl")
	private PcMeetingDaoImpl pcMeetingDaoImpl;
	public void setPcMeetingDaoImpl(PcMeetingDaoImpl pcMeetingDaoImpl) {
		this.pcMeetingDaoImpl = pcMeetingDaoImpl;
	}
	
	@Resource(name="PcMeetingContentDaoImpl")
	private PcMeetingContentDaoImpl pcMeetingContentDaoImpl;
	public void setPcMeetingContentDaoImpl(PcMeetingContentDaoImpl pcMeetingContentDaoImpl) {
		this.pcMeetingContentDaoImpl = pcMeetingContentDaoImpl;
	}
	
	@Resource(name="PcMeetingAsenceDaoImpl")
	private PcMeetingAsenceDaoImpl pcMeetingAsenceDaoImpl;
	public void setPcMeetingAsenceDaoImpl(PcMeetingAsenceDaoImpl pcMeetingAsenceDaoImpl) {
		this.pcMeetingAsenceDaoImpl = pcMeetingAsenceDaoImpl;
	}
	
	@Resource(name="PcAgencyRelationDaoImpl")
	private PcAgencyRelationDaoImpl pcAgencyRelationDaoImpl;
	public void setPcAgencyRelationDaoImpl(PcAgencyRelationDaoImpl pcAgencyRelationDaoImpl) {
		this.pcAgencyRelationDaoImpl = pcAgencyRelationDaoImpl;
	}
	
	private List<PcMeetingVo> createOriginalMeetingList(Integer agencyId, Integer year, Integer typeId) {
		List<PcMeetingVo> list = new ArrayList<PcMeetingVo>();
		for (int i = 1; i <= 4; i++) {
			PcMeetingVo meeting = new PcMeetingVo();
			meeting.setYear(year);
			meeting.setQuarter(i);
			meeting.setTypeId(typeId);
			meeting.setAgencyId(agencyId);
			meeting.setStatusId(2);
			list.add(meeting);
		}
		return list;
	}
	
	private List<PcMeetingVo> createOriginalCommitteeMeetingList(Integer agencyId, Integer year, Integer typeId) {
		List<PcMeetingVo> list = new ArrayList<PcMeetingVo>();
		for (int i = 1; i <= 12; i++) {
			PcMeetingVo meeting = new PcMeetingVo();
			meeting.setYear(year);
			if (i <= 3) {
				meeting.setQuarter(1);
			} else if (i <= 6) {
				meeting.setQuarter(2);
			} else if (i <= 9) {
				meeting.setQuarter(3);
			} else {
				meeting.setQuarter(4);
			}
			meeting.setMonth(i);
			meeting.setTypeId(typeId);
			meeting.setAgencyId(agencyId);
			meeting.setStatusId(2);
			list.add(meeting);
		}
		return list;
	}
	
	public List<PcMeetingVo> getBranchCommitteeMeetingList(Integer agencyId, Integer year) {
		// At least once every month.
//		List<PcMeetingVo> list = createOriginalCommitteeMeetingList(agencyId, year, 8);
		List<PcMeetingVo> list = new ArrayList<PcMeetingVo>();
		List<PcMeeting> meetingList = pcMeetingDaoImpl.getMeetingList(agencyId, year, 8);
		if (meetingList == null || meetingList.size() == 0) {
			return list;
		}
//		List<List<PcMeetingVo>> meetingListArry = new ArrayList<List<PcMeetingVo>>();
//		for (int i = 1; i <= 12; i++) {
//			List<PcMeetingVo> meetingListItem = new ArrayList<PcMeetingVo>();
//			meetingListArry.add(meetingListItem);
//		}
//		for (PcMeeting meetingItem : meetingList) {
//			Integer meetingMonth = meetingItem.getMonth();
//			if (meetingMonth == null) {
//				continue;
//			}
//			PcMeetingVo meetingItemVo = PcMeetingVo.fromPcMeeting(meetingItem);
//			String asenceMemberIds = pcMeetingAsenceDaoImpl.getMemberIdsByMeetingId(meetingItemVo.getId());
//			meetingItemVo.setAsenceMemberIds(asenceMemberIds);
//			meetingListArry.get(meetingMonth - 1).add(meetingItemVo);
//		}
//		int len = list.size();
//		while (len-- > 0) {
//			List<PcMeetingVo> meetingListItem = meetingListArry.get(len);
//			if (meetingListItem.size() > 0) {
//				list.remove(len);
//			}
//		}
//		for (List<PcMeetingVo> meetingListItem : meetingListArry) {
//			list.addAll(meetingListItem);
//		}
		for (PcMeeting meetingItem : meetingList) {
			PcMeetingVo meetingItemVo = PcMeetingVo.fromPcMeeting(meetingItem);
			String asenceMemberIds = pcMeetingAsenceDaoImpl.getMemberIdsByMeetingId(meetingItemVo.getId());
			meetingItemVo.setAsenceMemberIds(asenceMemberIds);
			list.add(meetingItemVo);
		}
		return list;
	}
	
	public List<PcMeetingVo> getBranchLifeMeetingList(Integer agencyId, Integer year) {
//		List<PcMeetingVo> list = createOriginalMeetingList(agencyId, year, 7);
		List<PcMeetingVo> list = new ArrayList<PcMeetingVo>();
		List<PcMeeting> meetingList = pcMeetingDaoImpl.getMeetingList(agencyId, year, 7);
		if (meetingList == null || meetingList.size() == 0) {
			return list;
		}
//		for (PcMeeting meetingItem : meetingList) {
//			for (int i = 0; i < list.size(); i++) {
//				PcMeetingVo meetingVo = list.get(i);
//				if (meetingVo.getQuarter().intValue() == meetingItem.getQuarter().intValue()) {
//					list.remove(i);
//					PcMeetingVo meetingVoItem = PcMeetingVo.fromPcMeeting(meetingItem);
//					String asenceMemberIds = pcMeetingAsenceDaoImpl.getMemberIdsByMeetingId(meetingVoItem.getId());
//					meetingVoItem.setAsenceMemberIds(asenceMemberIds);
//					list.add(i, meetingVoItem);
//				}
//			}
//		}
		for (PcMeeting meetingItem : meetingList) {
			PcMeetingVo meetingVoItem = PcMeetingVo.fromPcMeeting(meetingItem);
			String asenceMemberIds = pcMeetingAsenceDaoImpl.getMemberIdsByMeetingId(meetingVoItem.getId());
			meetingVoItem.setAsenceMemberIds(asenceMemberIds);
			list.add(meetingVoItem);
		}
		return list;
	}

	public List<PcMeetingVo> getBranchMemberMeetingList(Integer agencyId, Integer year) {
		// At least once every quarter.
//		List<PcMeetingVo> list = createOriginalMeetingList(agencyId, year, 6);
		List<PcMeetingVo> list = new ArrayList<PcMeetingVo>();
		List<PcMeeting> meetingList = pcMeetingDaoImpl.getMeetingList(agencyId, year, 6);
		if (meetingList == null || meetingList.size() == 0) {
			return list;
		}
//		List<PcMeetingVo> meetingList1 = new ArrayList<PcMeetingVo>();
//		List<PcMeetingVo> meetingList2 = new ArrayList<PcMeetingVo>();
//		List<PcMeetingVo> meetingList3 = new ArrayList<PcMeetingVo>();
//		List<PcMeetingVo> meetingList4 = new ArrayList<PcMeetingVo>();
//		for (PcMeeting meetingItem : meetingList) {
//			PcMeetingVo meetingVoItem = PcMeetingVo.fromPcMeeting(meetingItem);
//			String asenceMemberIds = pcMeetingAsenceDaoImpl.getMemberIdsByMeetingId(meetingVoItem.getId());
//			meetingVoItem.setAsenceMemberIds(asenceMemberIds);
//			if (meetingItem.getQuarter() == 1) {
//				meetingList1.add(meetingVoItem);
//			} else if (meetingItem.getQuarter() == 2) {
//				meetingList2.add(meetingVoItem);
//			} else if (meetingItem.getQuarter() == 3) {
//				meetingList3.add(meetingVoItem);
//			} else {
//				meetingList4.add(meetingVoItem);
//			}
//		}
//		int len = list.size();
//		while (len-- > 0) {
//			PcMeetingVo originalItem = list.get(len);
//			if ((originalItem.getQuarter() == 1 && meetingList1.size() > 0) ||
//				(originalItem.getQuarter() == 2 && meetingList2.size() > 0) ||
//				(originalItem.getQuarter() == 3 && meetingList3.size() > 0) ||
//				(originalItem.getQuarter() == 4 && meetingList4.size() > 0)) {
//				list.remove(len);
//			}
//		}
//		list.addAll(meetingList1);
//		list.addAll(meetingList2);
//		list.addAll(meetingList3);
//		list.addAll(meetingList4);
		for (PcMeeting meetingItem : meetingList) {
			PcMeetingVo meetingVoItem = PcMeetingVo.fromPcMeeting(meetingItem);
			String asenceMemberIds = pcMeetingAsenceDaoImpl.getMemberIdsByMeetingId(meetingVoItem.getId());
			meetingVoItem.setAsenceMemberIds(asenceMemberIds);
			list.add(meetingVoItem);
		}
		return list;
	}

	public List<PcMeetingVo> getClassMeetingList(Integer agencyId, Integer year) {
//		List<PcMeetingVo> list = createOriginalMeetingList(agencyId, year, 5);
		List<PcMeetingVo> list = new ArrayList<PcMeetingVo>();
		List<PcMeeting> meetingList = pcMeetingDaoImpl.getMeetingList(agencyId, year, 5);
		if (meetingList == null || meetingList.size() == 0) {
			return list;
		}
//		for (PcMeeting meetingItem : meetingList) {
//			for (int i = 0; i < list.size(); i++) {
//				PcMeetingVo meetingVo = list.get(i);
//				if (meetingVo.getQuarter().intValue() == meetingItem.getQuarter().intValue()) {
//					list.remove(i);
//					PcMeetingVo meetingVoItem = PcMeetingVo.fromPcMeeting(meetingItem);
//					String asenceMemberIds = pcMeetingAsenceDaoImpl.getMemberIdsByMeetingId(meetingVoItem.getId());
//					meetingVoItem.setAsenceMemberIds(asenceMemberIds);
//					list.add(i, meetingVoItem);
//				}
//			}
//		}
		for (PcMeeting meetingItem : meetingList) {
			PcMeetingVo meetingVoItem = PcMeetingVo.fromPcMeeting(meetingItem);
			String asenceMemberIds = pcMeetingAsenceDaoImpl.getMemberIdsByMeetingId(meetingVoItem.getId());
			meetingVoItem.setAsenceMemberIds(asenceMemberIds);
			list.add(meetingVoItem);
		}
		return list;
	}

	public PcMeetingContentVo getMeetingContent(Integer meetingId) {
		PcMeetingContent content = pcMeetingContentDaoImpl.getMeetingContent(meetingId);
		return PcMeetingContentVo.fromPcMeetingContent(content);
	}

	public List<PcMeetingVo> getOtherMeetingList(Integer agencyId, Integer year) {
//		List<PcMeetingVo> list = createOriginalMeetingList(agencyId, year, 9);
		List<PcMeetingVo> list = new ArrayList<PcMeetingVo>();
		List<PcMeeting> meetingList = pcMeetingDaoImpl.getMeetingList(agencyId, year, 9);
		if (meetingList == null || meetingList.size() == 0) {
			return list;
		}
		for (PcMeeting meetingItem : meetingList) {
			PcMeetingVo meetingVoItem = PcMeetingVo.fromPcMeeting(meetingItem);
			String asenceMemberIds = pcMeetingAsenceDaoImpl.getMemberIdsByMeetingId(meetingVoItem.getId());
			meetingVoItem.setAsenceMemberIds(asenceMemberIds);
			list.add(meetingVoItem);
		}
//		for (PcMeeting meetingItem : meetingList) {
//			for (int i = 0; i < list.size(); i++) {
//				PcMeetingVo meetingVo = list.get(i);
//				if (meetingVo.getQuarter().intValue() == meetingItem.getQuarter().intValue()) {
//					list.remove(i);
//					PcMeetingVo meetingVoItem = PcMeetingVo.fromPcMeeting(meetingItem);
//					String asenceMemberIds = pcMeetingAsenceDaoImpl.getMemberIdsByMeetingId(meetingVoItem.getId());
//					meetingVoItem.setAsenceMemberIds(asenceMemberIds);
//					list.add(i, meetingVoItem);
//				}
//			}
//		}
		return list;
	}

	public void submitMeeting(PcMeetingVo meeting) {
		pcMeetingDaoImpl.submitMeeting(PcMeetingVo.toPcMeeting(meeting));
	}

	public void createMeeting(PcMeetingVo meeting) {
		if (meeting == null) {
			return;
		}
		PcMeeting pcMeeting = pcMeetingDaoImpl.createMeeting(PcMeetingVo.toPcMeeting(meeting));
		if (pcMeeting != null && meeting.getContent() != null) {
			PcMeetingContentVo content = meeting.getContent();
			content.setMeetingId(pcMeeting.getId());
			pcMeetingContentDaoImpl.createContent(PcMeetingContentVo.toPcMeetingContent(content));
		}
		if (pcMeeting != null && meeting.getAsenceMemberIds() != null && !meeting.getAsenceMemberIds().equals("")) {
			pcMeetingAsenceDaoImpl.createAsence(pcMeeting.getId(), meeting.getAsenceMemberIds());
		}
	}

	public void updateMeeting(PcMeetingVo meeting) {
		pcMeetingDaoImpl.updateMeeting(PcMeetingVo.toPcMeeting(meeting));
		if (meeting.getContent() != null) {
			pcMeetingContentDaoImpl.upateContent(PcMeetingContentVo.toPcMeetingContent(meeting.getContent()));
		}
		if (meeting.getAsenceMemberIds() != null && !meeting.getAsenceMemberIds().equals("")) {
			pcMeetingAsenceDaoImpl.updateAsence(meeting.getId(), meeting.getAsenceMemberIds());
		}
	}
	
	public void updateMeetingStatus(Integer meetingId, Integer statusId) {
		pcMeetingDaoImpl.updateMeetingStatus(meetingId, statusId);
	}	
	
	public void evaluateMeeting(Integer meetingId, PcMeetingContentVo contentVo) {
		contentVo.setMeetingId(meetingId);
		contentVo.setType(3);
		PcMeeting pcMeeting = pcMeetingDaoImpl.getMeetingById(meetingId);
		pcMeeting.setStatusId(4);
		pcMeetingDaoImpl.updateMeeting(pcMeeting);
		
		PcMeetingContent pcMeetingContent = pcMeetingContentDaoImpl.getContentBymeetingIdAndType(meetingId, 3);
		if (pcMeetingContent == null) {
			pcMeetingContentDaoImpl.createContent(PcMeetingContentVo.toPcMeetingContent(contentVo));
		} else {
			pcMeetingContent.setContent(contentVo.getContent());
			pcMeetingContent.setMemberName(contentVo.getMemberName());
			pcMeetingContentDaoImpl.upateContent(pcMeetingContent);
		}
	}

	public List<PcMeetingVo> getCommitChildrenMeeting(Integer agencyId, Integer year) {
		List<PcMeetingVo> list = new ArrayList<PcMeetingVo>();
		List<PcAgencyRelation> agencyRelationList = pcAgencyRelationDaoImpl.getChildrenByParentId(agencyId);
		if (agencyRelationList == null || agencyRelationList.size() == 0) {
			return null;
		}
		List<Integer> agencyIds = new ArrayList<Integer>();
		for (PcAgencyRelation agencyRelation : agencyRelationList) {
			agencyIds.add(agencyRelation.getAgencyId());
		}
		List<PcMeeting> meetingList = new ArrayList<PcMeeting>();
		meetingList = pcMeetingDaoImpl.getCommitMeetingListByAgencyIds(agencyIds, year);
		if (meetingList != null && meetingList.size() > 0) {
			for (PcMeeting meeting : meetingList) {
				list.add(PcMeetingVo.fromPcMeeting(meeting));
			}
		}
		return list;
	}

	public PcMeetingContentVo getEvaluateInfo(Integer meetingId) {
		PcMeetingContent content = pcMeetingContentDaoImpl.getContentBymeetingIdAndType(meetingId, 3);
		if (content == null) {
			return null;
		}
		return PcMeetingContentVo.fromPcMeetingContent(content);
	}

	public List<PcMeetingVo> getAlertInfo(Integer agencyId, Integer year,
			Integer quarter) {
		List<PcMeetingVo> list = new ArrayList<PcMeetingVo>();
		// class.
		PcMeeting classMeeting = pcMeetingDaoImpl.getMeeting(agencyId, year, quarter, 5);
		if (classMeeting != null) {
			list.add(PcMeetingVo.fromPcMeeting(classMeeting));
		} else {
			PcMeetingVo meetingVo = new PcMeetingVo();
			meetingVo.setTypeId(5);
			meetingVo.setStatusId(0);
			list.add(meetingVo);
		}
		// branch members.
		PcMeeting branchMemberMeeting = pcMeetingDaoImpl.getMeeting(agencyId, year, quarter, 6);
		if (branchMemberMeeting != null) {
			list.add(PcMeetingVo.fromPcMeeting(branchMemberMeeting));
		} else {
			PcMeetingVo meetingVo = new PcMeetingVo();
			meetingVo.setTypeId(6);
			meetingVo.setStatusId(0);
			list.add(meetingVo);
		}
		// branch committee.
		PcMeeting branchCommitteeMeeting = pcMeetingDaoImpl.getMeeting(agencyId, year, quarter, 8);
		if (branchCommitteeMeeting != null) {
			list.add(PcMeetingVo.fromPcMeeting(branchCommitteeMeeting));
		} else {
			PcMeetingVo meetingVo = new PcMeetingVo();
			meetingVo.setTypeId(8);
			meetingVo.setStatusId(0);
			list.add(meetingVo);
		}
		// branch life.
		PcMeeting branchLifeMeeting = pcMeetingDaoImpl.getMeeting(agencyId, year, quarter, 7);
		if (branchLifeMeeting != null) {
			list.add(PcMeetingVo.fromPcMeeting(branchLifeMeeting));
		} else {
			PcMeetingVo meetingVo = new PcMeetingVo();
			meetingVo.setTypeId(7);
			meetingVo.setStatusId(0);
			list.add(meetingVo);
		}
		return list;
	}

	public PcMeetingContentVo getMeetingComment(PcMeetingVo meetingVo) {
		PcMeetingContent content = pcMeetingContentDaoImpl.getMeetingComment(meetingVo.getId());
		if (content == null) {
			return null;
		}
		return PcMeetingContentVo.fromPcMeetingContent(content);
	}
}
