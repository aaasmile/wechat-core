
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
    public List<EstoreProductEntity> selectProductList(EstoreProductSearch estoreProductSearch) {
        EstoreProductSearch productSearch = estoreProductSearch.clone();
        productSearch.setPageNum(null);
        productSearch.setPageSize(null);
        List<EstoreProductListResult> listEstoreProductListResult = estoreProductMapper.selectProductList(productSearch);
//        PageHelper.startPage(estoreProductSearch.getPageNum(),estoreProductSearch.getPageSize(),true);
        EstoreProductEntity estoreProductEntity;
        EstoreProductSpecEntity estoreProductSpecEntity;
        List<EstoreProductEntity> listEstoreProductEntity = new ArrayList<>();
        HashMap<Long, EstoreProductEntity> mapEstoreProductEntity = new HashMap<>();
        HashMap<Long, List<EstoreProductSpecEntity>> mapEstoreProductSpecEntity = new HashMap<>();
        for (EstoreProductListResult result : listEstoreProductListResult) {
            if (!mapEstoreProductEntity.containsKey(result.getId())) {
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
                EstoreProductCategory estoreProductCategory = new EstoreProductCategory();
                estoreProductCategory.setProductSaleId(result.getSaleId());
                estoreProductCategory.setWechatId(result.getWechatId());
                List<EstoreProductCategory> listEstoreProductCategory
                        = estoreProductCategoryMapper.select(estoreProductCategory);
                ArrayList<Long> arrCat = new ArrayList<>(listEstoreProductCategory.size());
                for (EstoreProductCategory cat : listEstoreProductCategory) {
                    arrCat.add(cat.getCategoryId());
                }
                EstoreProductTag estoreProductTag = new EstoreProductTag();
                estoreProductTag.setProductSaleId(result.getSaleId());
                estoreProductTag.setWechatId(result.getWechatId());
                List<EstoreProductTag> listEstoreProductTag
                        = estoreProductTagMapper.select(estoreProductTag);
                ArrayList<Long> arrTag = new ArrayList<>(listEstoreProductTag.size());
                for (EstoreProductTag tag : listEstoreProductTag) {
                    arrTag.add(tag.getTagId());
                }
                String strCat = StringUtils.join(arrCat, ",");
                String strTag = StringUtils.join(arrTag, ",");
                estoreProductEntity.setCategory(strCat);
                estoreProductEntity.setTag(strTag);
                mapEstoreProductEntity.put(result.getId(), estoreProductEntity);
            }
            estoreProductSpecEntity = new EstoreProductSpecEntity();
            estoreProductSpecEntity.setId(result.getSpId());
            estoreProductSpecEntity.setSku(result.getSku());
            estoreProductSpecEntity.setMarketPrice(result.getMarketPrice());
            estoreProductSpecEntity.setPrice(result.getPrice());
            estoreProductSpecEntity.setPoint(result.getPoint());
            estoreProductSpecEntity.setSpecType(result.getSpSpecType());
            estoreProductSpecEntity.setSpecValue(result.getSpSpecValue());
            estoreProductSpecEntity.setVolume(result.getVolume());
            estoreProductSpecEntity.setWeight(result.getWeight());
            estoreProductSpecEntity.setStock(result.getStock());
            estoreProductSpecEntity.setStatus(result.getSpStatus());
            if (!mapEstoreProductSpecEntity.containsKey(result.getId())){
                List<EstoreProductSpecEntity> listSpec = new ArrayList<>();
                listSpec.add(estoreProductSpecEntity);
                mapEstoreProductSpecEntity.put(result.getId(), listSpec);
            } else {
                mapEstoreProductSpecEntity.get(result.getId()).add(estoreProductSpecEntity);
            }
        }

        for (Map.Entry<Long, EstoreProductEntity> entry : mapEstoreProductEntity.entrySet()) {
            entry.getValue().setListSpec(mapEstoreProductSpecEntity.get(entry.getKey()));
            listEstoreProductEntity.add(entry.getValue());
        }
        Integer pageNum = estoreProductSearch.getPageNum();
        Integer pageSize = estoreProductSearch.getPageSize();
        Integer fromIdx = (pageNum-1)*pageSize;
        Integer toIdx = (listEstoreProductEntity.size() < pageNum*pageSize) ? listEstoreProductEntity.size() : pageNum*pageSize;
        return listEstoreProductEntity.subList(fromIdx, toIdx);
//        return (Page<EstoreProductEntity>) listEstoreProductEntity;
    }

    @Override
    public EstoreProductEntity getEstoreProduct(Long pid, Long wechatId) {
        EstoreProductEntity estoreProductEntity = new EstoreProductEntity();
        // 获取商品信息
        EstoreProduct estoreProduct = estoreProductMapper.selectByPrimaryKey(pid);
        estoreProductEntity.setId(pid);
        estoreProductEntity.setName(estoreProduct.getName());
        estoreProductEntity.setDescription(estoreProduct.getDescription());
        estoreProductEntity.setSpecType(estoreProduct.getSpecType());
        estoreProductEntity.setSpecMeta(estoreProduct.getSpecMeta());
        estoreProductEntity.setStatus(estoreProduct.getStatus());
        estoreProductEntity.setExtAttr(estoreProduct.getExtAttr());
        EstoreProductImage estoreProductImage = new EstoreProductImage();
        estoreProductImage.setProductId(pid);
        estoreProductImage.setWechatId(wechatId);
        estoreProductImage.setType((byte)0);
        List<EstoreProductImage> listImg = estoreProductImageMapper.select(estoreProductImage);
        List<EstoreProductImageEntity> listImgEntity = new ArrayList<>(listImg.size());
        EstoreProductImageEntity estoreProductImageEntity;
        for (EstoreProductImage img : listImg) {
            estoreProductImageEntity = new EstoreProductImageEntity();
            estoreProductImageEntity.setId(img.getId());
            estoreProductImageEntity.setType(img.getType());
            estoreProductImageEntity.setSeq(img.getSeq());
            estoreProductImageEntity.setMaterialId(img.getMaterialId());
            EstoreMaterial estoreMaterial = estoreMaterialMapper.selectByPrimaryKey(img.getMaterialId());
            if (estoreMaterial != null) {
                estoreProductImageEntity.setUrl(estoreMaterial.getUrl());
                estoreProductImageEntity.setTitle(estoreMaterial.getTitle());
                estoreProductImageEntity.setTag(estoreMaterial.getTag());
            }
            listImgEntity.add(estoreProductImageEntity);
        }
        estoreProductEntity.setListImg(listImgEntity);
        // 获取商品销售信息
        EstoreProductSale estoreProductSale = new EstoreProductSale();
        estoreProductSale.setWechatId(wechatId);
        estoreProductSale.setProductId(pid);
        estoreProductSale = estoreProductSaleMapper.selectOne(estoreProductSale);
        Long saleId = estoreProductSale.getId();
        estoreProductEntity.setSaleId(saleId);
        estoreProductEntity.setOnSale(estoreProductSale.getOnSale());
        estoreProductEntity.setDeliveryFree(estoreProductSale.getDeliveryFree());
        estoreProductEntity.setDeliveryTplId(estoreProductSale.getDeliveryTplId());
        // 获取商品规格
        EstoreProductSpec estoreProductSpec = new EstoreProductSpec();
        estoreProductSpec.setWechatId(wechatId);
        estoreProductSpec.setProductId(pid);
        List<EstoreProductSpec> listEstoreProductSpec = estoreProductSpecMapper.select(estoreProductSpec);
        List<EstoreProductSpecEntity> listSpec = new ArrayList<>(listEstoreProductSpec.size());
        EstoreProductSpecEntity estoreProductSpecEntity;
        for (EstoreProductSpec spec : listEstoreProductSpec) {
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
            EstoreProductImage estoreProductSpecImage = new EstoreProductImage();
            estoreProductSpecImage.setProductSpecId(spec.getId());
            estoreProductSpecImage.setWechatId(wechatId);
            estoreProductSpecImage.setType((byte)1);
            List<EstoreProductImage> listSpecImg = estoreProductImageMapper.select(estoreProductSpecImage);
            List<EstoreProductImageEntity> listSpecImgEntity = new ArrayList<>(listSpecImg.size());
            for (EstoreProductImage specImg : listSpecImg) {
                estoreProductImageEntity = new EstoreProductImageEntity();
                estoreProductImageEntity.setId(specImg.getId());
                estoreProductImageEntity.setType(specImg.getType());
                estoreProductImageEntity.setSeq(specImg.getSeq());
                estoreProductImageEntity.setMaterialId(specImg.getMaterialId());
                listSpecImgEntity.add(estoreProductImageEntity);
            }
            estoreProductSpecEntity.setListImg(listSpecImgEntity);
            listSpec.add(estoreProductSpecEntity);
        }
        estoreProductEntity.setListSpec(listSpec);
        // 获取商品分类
        EstoreProductCategory estoreProductCategory = new EstoreProductCategory();
        estoreProductCategory.setProductSaleId(saleId);
        estoreProductCategory.setWechatId(wechatId);
        List<EstoreProductCategory> listCat = estoreProductCategoryMapper.select(estoreProductCategory);
        List<String> arrCat = new ArrayList<>(listCat.size());
        for (EstoreProductCategory cat : listCat) {
            arrCat.add(cat.getCategoryId() + "");
        }
        estoreProductEntity.setCategory(StringUtils.join(arrCat.toArray(), ","));
        // 获取商品标签
        EstoreProductTag estoreProductTag = new EstoreProductTag();
        estoreProductTag.setProductSaleId(saleId);
        estoreProductTag.setWechatId(wechatId);
        List<EstoreProductTag> listTag = estoreProductTagMapper.select(estoreProductTag);
        List<String> arrTag = new ArrayList<>(listTag.size());
        for (EstoreProductTag tag : listTag) {
            arrTag.add(tag.getTagId() + "");
        }
        estoreProductEntity.setTag(StringUtils.join(arrTag.toArray(), ","));

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
            estoreProduct.setStatus((byte)1);
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
        for (String  catId : arrCat) {
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
        for (String  tagId : arrTag) {
            estoreProductTag = new EstoreProductTag();
            estoreProductTag.setProductSaleId(saleId);
            estoreProductTag.setTagId(Long.parseLong(tagId));
            estoreProductTag.setWechatId(wechatId);
            estoreProductTagMapper.insert(estoreProductTag);
        }

        // 更新商品图片
        List<EstoreProductImageEntity> listImg = productEntity.getListImg();
        EstoreProductImage estoreProductImage;
        for (EstoreProductImageEntity entity: listImg) {
            estoreProductImage = new EstoreProductImage();
            estoreProductImage.setMaterialId(entity.getMaterialId());
            estoreProductImage.setSeq(entity.getSeq());
            estoreProductImage.setType(entity.getType());
            estoreProductImage.setProductId(productId);
            estoreProductImage.setWechatId(wechatId);
            if (entity.getId() != null) {
                estoreProductImage.setId(entity.getId());
                if(entity.getIsDel() != null && entity.getIsDel() == (byte)1) {
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
        for (EstoreProductSpecEntity entity: listSepc) {
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
                if(entity.getIsDel() != null && entity.getIsDel() == (byte)1) {
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
            for (EstoreProductImageEntity entitySpec: listSpecImg) {
                estoreProductSpecImage = new EstoreProductImage();
                estoreProductSpecImage.setMaterialId(entitySpec.getMaterialId());
                estoreProductSpecImage.setSeq(entitySpec.getSeq());
                estoreProductSpecImage.setType(entitySpec.getType());
                estoreProductSpecImage.setProductSpecId(prodctSepcId);
                estoreProductSpecImage.setWechatId(wechatId);
                if (entitySpec.getId() != null) {
                    estoreProductSpecImage.setId(entitySpec.getId());
                    if(entitySpec.getIsDel() != null && entitySpec.getIsDel() == (byte)1) {
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
