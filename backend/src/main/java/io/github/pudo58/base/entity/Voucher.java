package io.github.pudo58.base.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.github.pudo58.constant.VoucherConst;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Voucher extends BaseEntity {
    private String name; // tên voucher
    private String code; // mã voucher
    private Integer discount; // phần trăm giảm giá
    private Integer minTotal; // số tiền tối thiểu để sử dụng voucher
    private Integer maxDiscount; // số tiền tối đa được giảm
    private Integer maxUsage; // số lần sử dụng tối đa'
    @Column(name = "`usage`")
    private Integer usage; // số lần đã sử dụng
    private String status; // active, inactive
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startDate; // ngày bắt đầu
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate; // ngày kết thúc
    private String description; // mô tả
    @Column(name = "`type`")
    private String type; // discount, fixed


    @Override
    public void prePersist() {
        super.prePersist();
        if (this.usage == null) {
            this.usage = 0;
        }
        if (this.status == null) {
            this.status = VoucherConst.STATUS_ACTIVE;
        }
    }

    public int getDiscountValue(int total) {
        if (total < this.minTotal) {
            return 0; // Không đủ điều kiện để sử dụng voucher
        }
        int discountValue;
        if (this.type.equals(VoucherConst.TYPE_FIXED)) {
            discountValue = this.discount; // Giảm giá cố định
        } else {
            discountValue = (int) ((this.discount / 100.0) * total);
            if (discountValue > this.maxDiscount) {
                discountValue = this.maxDiscount; // Giới hạn số tiền giảm tối đa
            }// Giảm giá phần trăm
        }
        return discountValue;
    }
}
