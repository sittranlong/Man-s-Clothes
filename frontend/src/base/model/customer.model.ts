import type {Order} from "@/base/model/order.model";

export class Customer {
    public static TYPE_ADULT = 'ADULT';
    public static TYPE_CHILDREN = 'CHILDREN';
    public static TYPE_BABY = 'BABY';
    id?: string;
    name?: string;
    email?: string;
    address?: string;
    type?: string;
    order?: Order;
}

export const CustomerType = [
    {value: Customer.TYPE_ADULT, text: 'Người lớn'},
    {value: Customer.TYPE_CHILDREN, text: 'Trẻ em'},
    {value: Customer.TYPE_BABY, text: 'Em bé'},
];

export const CustomerTypeText = (type: string | undefined) => {
    if (type) {
        const customerType = CustomerType.find(item => item.value === type);
        return customerType ? customerType.text : '';
    }
    return '';
};