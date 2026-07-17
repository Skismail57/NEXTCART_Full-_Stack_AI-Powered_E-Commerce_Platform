package com.nextcart.config;

import com.nextcart.controller.*;
import com.nextcart.exception.GlobalExceptionMapper;
import jakarta.ws.rs.ApplicationPath;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("")
public class NextCartApplication extends ResourceConfig {
    public NextCartApplication() {
        register(CategoryController.class);
        register(ProductController.class);
        register(BrandController.class);
        register(SubCategoryController.class);
        register(AuthController.class);
        register(CartController.class);
        register(WishlistController.class);
        register(OrderController.class);
        register(CouponController.class);
        register(UserAddressController.class);
        register(BannerController.class);
        register(HeroBannerController.class);
        register(TestController.class);
        register(UserController.class);
        register(CmsPageController.class);
        register(SystemSettingController.class);
        register(OfferController.class);
        register(InventoryController.class);
        register(ExchangeRequestController.class);
        register(HomepageSectionController.class);
        register(HomepageBannerController.class);
        register(AnnouncementBarController.class);
        register(FlashSaleController.class);
        register(TestimonialController.class);
        register(FeaturedCollectionController.class);
        register(PopupController.class);
        register(EmailTemplateController.class);
        register(PermissionController.class);
        register(RolePermissionController.class);
        register(MenuItemController.class);
        register(AuditLogController.class);
        register(SupportTicketController.class);
        register(TicketMessageController.class);
        register(NotificationController.class);
        register(GlobalExceptionMapper.class);
        register(MultiPartFeature.class);
    }
}
