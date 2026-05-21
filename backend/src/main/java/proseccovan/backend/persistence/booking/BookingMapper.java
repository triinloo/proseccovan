package proseccovan.backend.persistence.booking;

import org.mapstruct.*;
import proseccovan.backend.controller.booking.dto.BookingResponseDto;
import proseccovan.backend.persistence.usercontact.UserContact;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface BookingMapper {

    @Mapping(source = "userContact.userName", target = "customerName")
    @Mapping(source = "userContact.phone", target = "phoneNumber")
    @Mapping(source = "booking.user.email", target = "email")
    @Mapping(source = "booking.address", target = "address")
    @Mapping(source = "booking.bookingTypeInfo", target = "bookingType")
    @Mapping(source = "booking.packageField.name", target = "packageType")
    @Mapping(source = "booking.packageField.description", target = "bookingInfo")
    @Mapping(source = "booking.eventDate", target = "bookingDate", qualifiedByName = "formatDate")
    @Mapping(source = "booking.status", target = "bookingStatus", qualifiedByName = "mapStatus")
    @Mapping(source = "booking.latitude", target = "latitude", qualifiedByName = "bigDecimalToString")
    @Mapping(source = "booking.longitude", target = "longitude", qualifiedByName = "bigDecimalToString")
    BookingResponseDto toDto(Booking booking, UserContact userContact);

    @Named("formatDate")
    default String formatDate(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    @Named("mapStatus")
    default String mapStatus(String status) {
        return BookingStatusMapper.toBookingStatus(status);
    }

    @Named("bigDecimalToString")
    default String bigDecimalToString(BigDecimal value) {
        return value != null ? value.toPlainString() : null;
    }
}