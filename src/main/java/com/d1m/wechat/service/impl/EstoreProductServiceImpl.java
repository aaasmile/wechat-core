
package com.d1m.wechat.service.impl;

import com.d1m.wechat.mapper.*;
import com.d1m.wechat.model.*;
import com.d1m.wechat.pamametermodel.EstoreProductEntity;
import com.d1m.wechat.pamametermodel.EstoreProductImageEntity;
import com.d1m.wechat.pamametermodel.EstoreProductSearch;
import com.d1m.wechat.pamametermodel.EstoreProductSpecEntity;
import com.d1m.wechat.service.IEstoreProductService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EstoreProductServiceImpl implements IEstoreProductService {
    private static final Logger log = LoggerFactory.getLogger(EstoreProductServiceImpl.class);
    @Autowired
    private EstoreProductMapper estoreProductMapper;
    @Autowired
    private EstoreProductSpecMapper estoreProductSpecMapper;
    @Autowired
    private EstoreProductImageMapper estoreProductImageMapper;
    @Autowired
    private EstoreProductSaleMapper estoreProductSaleMapper;
    @Autowired
    private EstoreProductCategoryMapper estoreProductCategoryMapper;
    @Autowired
    private EstoreProductTagMapper estoreProductTagMapper;
    @Autowired
    private EstoreMaterialMapper estoreMaterialMapper;

    @Override
    public Page<EstoreProductEntity> selectProductList(EstoreProductSearch estoreProductSearch) {
        PageHelper.startPage(estoreProductSearch.getPageNum(), estoreProductSearch.getPageSize(), true);
        List<EstoreProductListResult> listEstoreProductListResult = estoreProductMapper.selectProductList(estoreProductSearch);
        EstoreProductEntity estoreProductEntity;
        EstoreProductSpecEntity estoreProductSpecEntity;
        EstoreProductImageEntity productImageEntity;
        List<EstoreProductSpecEntity> listEstoreProductSpecEntity;
        List<EstoreProductImageEntity> listEstoreProductImageEntity;
        List<EstoreProductEntity> listEstoreProductEntity = new Page<>();
        for (EstoreProductListResult result : listEstoreProductListResult) {
            estoreProductEntity = new EstoreProductEntity();
            estoreProductEntity.setId(result.getId());
            estoreProductEntity.setName(result.getName());
            estoreProductEntity.setDescription(result.getDescription());
            estoreProductEntity.setSpecType(result.getSpecType());
            estoreProductEntity.setSpecMeta(result.getSpecMeta());
            estoreProductEntity.setExtAttr(result.getExtAttr());
            estoreProductEntity.setStatus(result.getStatus());
            estoreProductEntity.setSaleId(result.getSaleId());
            estoreProductEntity.setOnSale(result.getOnSale());
            estoreProductEntity.setDeliveryFree(result.getDeliveryFree());
            estoreProductEntity.setDeliveryTplId(result.getDeliveryTplId());
            estoreProductEntity.setWechatId(result.getWechatId());
            List<EstoreProductImage> listProductImage = result.getListProductImage();
            if (listProductImage != null) {
                listEstoreProductImageEntity = new ArrayList<>(listProductImage.size());
                for (EstoreProductImage productImage : listProductImage) {
                    productImageEntity = new EstoreProductImageEntity();
                    productImageEntity.setMaterialId(productImage.getMaterialId());
                    productImageEntity.setSeq(productImage.getSeq());
                    productImageEntity.setType(productImage.getType());
                    productImageEntity.setTag(productImage.getTag());
                    productImageEntity.setTitle(productImage.getTitle());
                    productImageEntity.setUrl(productImage.getUrl());
                    listEstoreProductImageEntity.add(productImageEntity);
                }
                estoreProductEntity.setListImg(listEstoreProductImageEntity);
            }
            List<EstoreProductCategory> listProductCategory = result.getListProductCategory();
            if (listProductCategory != null) {
                ArrayList<Long> arrCat = new ArrayList<>(listProductCategory.size());
                for (EstoreProductCategory cat : listProductCategory) {
                    arrCat.add(cat.getCategoryId());
                }
                String strCat = StringUtils.join(arrCat, ",");
                estoreProductEntity.setCategory(strCat);
            }
            List<EstoreProductTag> listEstoreProductTag = result.getListProductTag();
            if (listEstoreProductTag != null) {
                ArrayList<Long> arrTag = new ArrayList<>(listEstoreProductTag.size());
                for (EstoreProductTag tag : listEstoreProductTag) {
                    arrTag.add(tag.getTagId());
                }
                String strTag = StringUtils.join(arrTag, ",");
                estoreProductEntity.setTag(strTag);
            }
            List<EstoreProductSpecListResult> listProductSpec = result.getListProductSpec();
            if (listProductSpec != null) {
                listEstoreProductSpecEntity = new ArrayList<>(listProductSpec.size());
                for (EstoreProductSpecListResult productSpec : listProductSpec) {
//                    if (estoreProductSearch.getSku() != null &&
//                            !productSpec.getSku().equals(estoreProductSearch.getSku())) {
//                        continue;
//                    }
                    estoreProductSpecEntity = new EstoreProductSpecEntity();
                    estoreProductSpecEntity.setId(productSpec.getId());
                    estoreProductSpecEntity.setSku(productSpec.getSku());
                    estoreProductSpecEntity.setMarketPrice(productSpec.getMarketPrice());
                    estoreProductSpecEntity.setPrice(productSpec.getPrice());
                    estoreProductSpecEntity.setPoint(productSpec.getPoint());
                    estoreProductSpecEntity.setSpecType(productSpec.getSpecType());
                    estoreProductSpecEntity.setSpecValue(productSpec.getSpecValue());
                    estoreProductSpecEntity.setVolume(productSpec.getVolume());
                    estoreProductSpecEntity.setWeight(productSpec.getWeight());
                    estoreProductSpecEntity.setStock(productSpec.getStock());
                    estoreProductSpecEntity.setStatus(productSpec.getStatus());
                    listProductImage = productSpec.getListProductSpecImage();
                    if (listProductImage != null) {
                        listEstoreProductImageEntity = new ArrayList<>(listProductImage.size());
                        for (EstoreProductImage productSpecImage : listProductImage) {
                            productImageEntity = new EstoreProductImageEntity();
                            productImageEntity.setMaterialId(productSpecImage.getMaterialId());
                            productImageEntity.setSeq(productSpecImage.getSeq());
                            productImageEntity.setType(productSpecImage.getType());
                            productImageEntity.setTag(productSpecImage.getTag());
                            productImageEntity.setTitle(productSpecImage.getTitle());
                            productImageEntity.setUrl(productSpecImage.getUrl());
                            listEstoreProductImageEntity.add(productImageEntity);
                        }
                        estoreProductSpecEntity.setListImg(listEstoreProductImageEntity);
                    }
                    listEstoreProductSpecEntity.add(estoreProductSpecEntity);
                }
                estoreProductEntity.setListSpec(listEstoreProductSpecEntity);
                listEstoreProductEntity.add(estoreProductEntity);
            }
        }
        return (Page<EstoreProductEntity>) listEstoreProductEntity;
    }

    @Override
    public EstoreProductEntity getEstoreProduct(Long productId, Long wechatId) {
        // 获取商品信息
        EstoreProductSearch estoreProductSearch = new EstoreProductSearch();
        estoreProductSearch.setProductId(productId);
        estoreProductSearch.setWechatId(wechatId);
        List<EstoreProductListResult> listEstoreProductListResult = estoreProductMapper.selectProductList(estoreProductSearch);
        EstoreProductEntity estoreProductEntity = new EstoreProductEntity();
        EstoreProductSpecEntity estoreProductSpecEntity;
        EstoreProductImageEntity productImageEntity;
        List<EstoreProductSpecEntity> listEstoreProductSpecEntity;
        List<EstoreProductImageEntity> listEstoreProductImageEntity;
        if (listEstoreProductListResult != null && listEstoreProductListResult.size()>0 ) {
            EstoreProductListResult estoreProduct = listEstoreProductListResult.get(0);
            estoreProductEntity.setId(productId);
            estoreProductEntity.setName(estoreProduct.getName());
            estoreProductEntity.setDescription(estoreProduct.getDescription());
            estoreProductEntity.setSpecType(estoreProduct.getSpecType());
            estoreProductEntity.setSpecMeta(estoreProduct.getSpecMeta());
            estoreProductEntity.setStatus(estoreProduct.getStatus());
            estoreProductEntity.setExtAttr(estoreProduct.getExtAttr());
            List<EstoreProductImage> listProductImage = estoreProduct.getListProductImage();
            if (listProductImage != null) {
                listEstoreProductImageEntity = new ArrayList<>(listProductImage.size());
                for (EstoreProductImage productImage : listProductImage) {
                    productImageEntity = new EstoreProductImageEntity();
                    productImageEntity.setMaterialId(productImage.getMaterialId());
                    productImageEntity.setSeq(productImage.getSeq());
                    productImageEntity.setType(productImage.getType());
                    productImageEntity.setTag(productImage.getTag());
                    productImageEntity.setTitle(productImage.getTitle());
                    productImageEntity.setUrl(productImage.getUrl());
                    listEstoreProductImageEntity.add(productImageEntity);
                }
                estoreProductEntity.setListImg(listEstoreProductImageEntity);
            }
            // 获取商品销售信息
            estoreProductEntity.setSaleId(estoreProduct.getSaleId());
            estoreProductEntity.setOnSale(estoreProduct.getOnSale());
            estoreProductEntity.setDeliveryFree(estoreProduct.getDeliveryFree());
            estoreProductEntity.setDeliveryTplId(estoreProduct.getDeliveryTplId());

            // 获取商品规格
            List<EstoreProductSpecListResult> listEstoreProductSpec = estoreProduct.getListProductSpec();
            if (listEstoreProductSpec != null) {
                listEstoreProductSpecEntity = new ArrayList<>(listEstoreProductSpec.size());
                for (EstoreProductSpecListResult spec : listEstoreProductSpec) {
                    estoreProductSpecEntity = new EstoreProductSpecEntity();
                    estoreProductSpecEntity.setId(spec.getId());
                    estoreProductSpecEntity.setSku(spec.getSku());
                    estoreProductSpecEntity.setWeight(spec.getWeight());
                    estoreProductSpecEntity.setVolume(spec.getVolume());
                    estoreProductSpecEntity.setSpecType(spec.getSpecType());
                    estoreProductSpecEntity.setSpecValue(spec.getSpecValue());
                    estoreProductSpecEntity.setMarketPrice(spec.getMarketPrice());
                    estoreProductSpecEntity.setPrice(spec.getPrice());
                    estoreProductSpecEntity.setPoint(spec.getPoint());
                    estoreProductSpecEntity.setStock(spec.getStock());
                    estoreProductSpecEntity.setStatus(spec.getStatus());
                    List<EstoreProductImage> listSpecImg = spec.getListProductSpecImage();
                    listEstoreProductImageEntity = new ArrayList<>(listSpecImg.size());
                    for (EstoreProductImage specImg : listSpecImg) {
                        productImageEntity = new EstoreProductImageEntity();
                        productImageEntity.setId(specImg.getId());
                        productImageEntity.setType(specImg.getType());
                        productImageEntity.setSeq(specImg.getSeq());
                        productImageEntity.setMaterialId(specImg.getMaterialId());
                        listEstoreProductImageEntity.add(productImageEntity);
                    }
                    estoreProductSpecEntity.setListImg(listEstoreProductImageEntity);
                    listEstoreProductSpecEntity.add(estoreProductSpecEntity);
                }
                estoreProductEntity.setListSpec(listEstoreProductSpecEntity);
            }
            List<EstoreProductCategory> listCat = estoreProduct.getListProductCategory();
            if (listCat != null) {
                List<String> arrCat = new ArrayList<>(listCat.size());
                for (EstoreProductCategory cat : listCat) {
                    arrCat.add(cat.getCategoryId() + "");
                }
                estoreProductEntity.setCategory(StringUtils.join(arrCat.toArray(), ","));
            }
            List<EstoreProductTag> listTag = estoreProduct.getListProductTag();
            if (listTag != null) {
                List<String> arrTag = new ArrayList<>(listTag.size());
                for (EstoreProductTag tag : listTag) {
                    arrTag.add(tag.getTagId() + "");
                }
                estoreProductEntity.setTag(StringUtils.join(arrTag.toArray(), ","));
            }
        }
        return estoreProductEntity;
    }

    @Override
    public void updateEstoreProduct(EstoreProductEntity productEntity, String action) {
        Date now = new Date();
        Long wechatId = productEntity.getWechatId();

        // 更新商品信息
        EstoreProduct estoreProduct = new EstoreProduct();
        estoreProduct.setName(productEntity.getName());
        estoreProduct.setDescription(productEntity.getDescription());
        estoreProduct.setModifyAt(now);
        estoreProduct.setExtAttr(productEntity.getExtAttr());
        estoreProduct.setSpecType(productEntity.getSpecType());
        estoreProduct.setSpecMeta(productEntity.getSpecMeta());
        estoreProduct.setWechatId(wechatId);
        if (productEntity.getId() != null) {
            estoreProduct.setId(productEntity.getId());
            estoreProduct.setStatus(productEntity.getStatus());
            estoreProductMapper.updateByPrimaryKeySelective(estoreProduct);
        } else {
            estoreProduct.setCreateAt(now);
            estoreProduct.setStatus((byte) 1);
            estoreProductMapper.insert(estoreProduct);
        }
        Long productId = estoreProduct.getId();

        // 更新商品销售信息
        EstoreProductSale estoreProductSale = new EstoreProductSale();
        estoreProductSale.setDeliveryFree(productEntity.getDeliveryFree());
        estoreProductSale.setDeliveryTplId(productEntity.getDeliveryTplId());
        estoreProductSale.setOnSale(productEntity.getOnSale());
        estoreProductSale.setProductId(productId);
        estoreProductSale.setWechatId(wechatId);
        if (productEntity.getSaleId() != null) {
            estoreProductSale.setId(productEntity.getSaleId());
            estoreProductSaleMapper.updateByPrimaryKey(estoreProductSale);
        } else {
            estoreProductSaleMapper.insert(estoreProductSale);
        }
        Long saleId = estoreProductSale.getId();

        // 更新商品类别
        EstoreProductCategory estoreProductCategory = new EstoreProductCategory();
        estoreProductCategory.setProductSaleId(saleId);
        estoreProductCategory.setWechatId(wechatId);
        estoreProductCategoryMapper.delete(estoreProductCategory);
        String[] arrCat = productEntity.getCategory().split(",");
        for (String catId : arrCat) {
            estoreProductCategory = new EstoreProductCategory();
            estoreProductCategory.setProductSaleId(saleId);
            estoreProductCategory.setCategoryId(Long.parseLong(catId));
            estoreProductCategory.setWechatId(wechatId);
            estoreProductCategoryMapper.insert(estoreProductCategory);
        }

        // 更新商品标签
        EstoreProductTag estoreProductTag = new EstoreProductTag();
        estoreProductTag.setProductSaleId(saleId);
        estoreProductTag.setWechatId(wechatId);
        estoreProductTagMapper.delete(estoreProductTag);
        String[] arrTag = productEntity.getTag().split(",");
        for (String tagId : arrTag) {
            estoreProductTag = new EstoreProductTag();
            estoreProductTag.setProductSaleId(saleId);
            estoreProductTag.setTagId(Long.parseLong(tagId));
            estoreProductTag.setWechatId(wechatId);
            estoreProductTagMapper.insert(estoreProductTag);
        }

        // 更新商品图片
        List<EstoreProductImageEntity> listImg = productEntity.getListImg();
        EstoreProductImage estoreProductImage;
        for (EstoreProductImageEntity entity : listImg) {
            estoreProductImage = new EstoreProductImage();
            estoreProductImage.setMaterialId(entity.getMaterialId());
            estoreProductImage.setSeq(entity.getSeq());
            estoreProductImage.setType(entity.getType());
            estoreProductImage.setProductId(productId);
            estoreProductImage.setWechatId(wechatId);
            if (entity.getId() != null) {
                estoreProductImage.setId(entity.getId());
                if (entity.getIsDel() != null && entity.getIsDel() == (byte) 1) {
                    estoreProductImageMapper.deleteByPrimaryKey(estoreProductImage);
                } else {
                    estoreProductImageMapper.updateByPrimaryKey(estoreProductImage);
                }
            } else {
                estoreProductImageMapper.insert(estoreProductImage);
            }
        }

        // 更新商品规格
        List<EstoreProductSpecEntity> listSepc = productEntity.getListSpec();
        EstoreProductSpec estoreProductSpec;
        for (EstoreProductSpecEntity entity : listSepc) {
            estoreProductSpec = new EstoreProductSpec();
            estoreProductSpec.setMarketPrice(entity.getMarketPrice());
            estoreProductSpec.setPrice(entity.getPrice());
            estoreProductSpec.setPoint(entity.getPoint());
            estoreProductSpec.setProductId(productId);
            estoreProductSpec.setSku(entity.getSku());
            estoreProductSpec.setSpecType(entity.getSpecType());
            estoreProductSpec.setSpecValue(entity.getSpecValue());
            estoreProductSpec.setStock(entity.getStock());
            estoreProductSpec.setVolume(entity.getVolume());
            estoreProductSpec.setWeight(entity.getWeight());
            estoreProductSpec.setStatus(entity.getStatus());
            estoreProductSpec.setWechatId(wechatId);
            estoreProductSpec.setCreateAt(now);
            estoreProductSpec.setModifyAt(now);
            if (entity.getId() != null) {
                estoreProductSpec.setId(entity.getId());
                if (entity.getIsDel() != null && entity.getIsDel() == (byte) 1) {
                    estoreProductSpecMapper.deleteByPrimaryKey(estoreProductSpec);
                } else {
                    estoreProductSpecMapper.updateByPrimaryKey(estoreProductSpec);
                }
            } else {
                estoreProductSpecMapper.insert(estoreProductSpec);
            }
            Long prodctSepcId = estoreProductSpec.getId();

            // 更新商品图片
            List<EstoreProductImageEntity> listSpecImg = entity.getListImg();
            EstoreProductImage estoreProductSpecImage;
            for (EstoreProductImageEntity entitySpec : listSpecImg) {
                estoreProductSpecImage = new EstoreProductImage();
                estoreProductSpecImage.setMaterialId(entitySpec.getMaterialId());
                estoreProductSpecImage.setSeq(entitySpec.getSeq());
                estoreProductSpecImage.setType(entitySpec.getType());
                estoreProductSpecImage.setProductSpecId(prodctSepcId);
                estoreProductSpecImage.setWechatId(wechatId);
                if (entitySpec.getId() != null) {
                    estoreProductSpecImage.setId(entitySpec.getId());
                    if (entitySpec.getIsDel() != null && entitySpec.getIsDel() == (byte) 1) {
                        estoreProductImageMapper.deleteByPrimaryKey(estoreProductSpecImage);
                    } else {
                        estoreProductImageMapper.updateByPrimaryKey(estoreProductSpecImage);
                    }
                } else {
                    estoreProductImageMapper.insert(estoreProductSpecImage);
                }
            }
        }
    }

}
