package com.shahinnazarov.paribas.api.resource;

import com.shahinnazarov.paribas.api.model.ApiResponse;
import com.shahinnazarov.paribas.api.model.BondOrderAdjustDto;
import com.shahinnazarov.paribas.api.model.BondOrderDto;
import com.shahinnazarov.paribas.api.model.BondOrderResultDto;
import com.shahinnazarov.paribas.db.entity.BondHistory;
import com.shahinnazarov.paribas.db.entity.BondOrder;
import com.shahinnazarov.paribas.db.entity.enums.BondHistoryActions;
import com.shahinnazarov.paribas.service.BondHistoryService;
import com.shahinnazarov.paribas.service.BondOrderService;
import com.shahinnazarov.paribas.util.Errors;
import com.shahinnazarov.paribas.util.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/orders")
public class BondOrderController extends GenericRestController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private BondOrderService bondOrderService;

    @Autowired
    private BondHistoryService bondHistoryService;

    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public ApiResponse<BondOrderResultDto> order(@Valid @RequestBody BondOrderDto bondOrderDto) {
        BondOrder bondOrder = new BondOrder();
        bondOrder.setIpAddress(request.getRemoteAddr());
        bondOrder.setAmount(bondOrderDto.getAmount());
        bondOrder.setTermLength(bondOrderDto.getTermLength());
        bondOrder.setClientDetail(bondOrderDto.getClientDetail());
        bondOrderService.save(bondOrder);

        BondOrderResultDto resultDto = new BondOrderResultDto();
        resultDto.setReferenceKey(bondOrder.getId());
        return generateOkResponse(resultDto);
    }

    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public ApiResponse<BondOrder> listOrders() {
        Collection<BondOrder> bondOrders = bondOrderService.listBondsByIpAddress(request.getRemoteAddr());
        return generateOkResponse(bondOrders);
    }

    @RequestMapping(
            path = "/{refKey}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public ApiResponse<BondOrder> adjustBond(@PathVariable("refKey") String orderReference,
                                             @Valid @RequestBody BondOrderAdjustDto adjustDto) {
        BondOrder bondOrder = bondOrderService.getBondOrderByReference(orderReference)
                .orElseThrow(() ->
                        new ResourceNotFoundException(Errors.RESOURCE_NOT_FOUND.getTitle(),
                                String.format(Errors.RESOURCE_NOT_FOUND.getDescription(), orderReference)));
        bondOrderService.adjust(bondOrder, adjustDto.getNewTermLength());
        return generateOkResponse(bondOrder);
    }

    @RequestMapping(
            path = "/{refKey}/histories",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public ApiResponse<BondHistory> listOrderHistories(@PathVariable("refKey") String orderReference) {
        Collection<BondHistory> bondOrders = bondHistoryService.listHistoriesByBondReference(orderReference);
        return generateOkResponse(bondOrders);
    }

}
