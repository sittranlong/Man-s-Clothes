import type {Category} from "@/base/model/category.model";

export class Tour {
    public id?: string;
    public name?: string;
    public description?: string;
    public location?: string;
    public departure?: string;
    public vehicle?: string;
    public startDate?: Date;
    public duration?: string;
    public quantityForAdult?: number;
    public quantityForChild?: number;
    public quantityForBaby?: number;
    public soldForAdult?: number;
    public soldForChild?: number;
    public soldForBaby?: number;
    public policy?: string;
    public priceAdult?: number
    public priceChild?: number;
    public priceBaby?: number;
    public image?: string;
    public imageData?: string;
    public attraction?: string;
    public category?: Category;
    public status?: string;
    public tourGalleryList?: TourGallery[];
    public tourScheduleList?: TourSchedule[];
}

export class TourGallery {
    id?: string;
    public image?: string;
    public description?: string;
    public title?: string;
    public altText?: string;
    public tour?: Tour;
}


export class TourSchedule {
    public id?: string;
    public name?: string = '';
    public description?: string= '';
    public tour?: Tour;
    public image?: string = '';
    public imageData?: string = '';
}

