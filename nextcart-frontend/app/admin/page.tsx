'use client';

import { useState, useEffect } from 'react';
import api from '@/lib/api';
import { 
  ApiResponse, 
  Product, 
  Category, 
  Brand, 
  Coupon, 
  Banner, 
  HeroBanner,
  SubCategory,
  ProductVariant
} from '@/types';

const TABS = [
  { id: 'products', label: 'Products' },
  { id: 'categories', label: 'Categories' },
  { id: 'subcategories', label: 'Sub Categories' },
  { id: 'brands', label: 'Brands' },
  { id: 'coupons', label: 'Coupons' },
  { id: 'banners', label: 'Banners' },
  { id: 'herobanners', label: 'Hero Banners' },
];

export default function AdminDashboard() {
  const [activeTab, setActiveTab] = useState('products');
  
  return (
    <div className="min-h-screen bg-gray-50 p-6">
      <div className="max-w-7xl mx-auto">
        <h1 className="text-3xl font-bold text-gray-900 mb-6">Admin Dashboard</h1>
        <div className="flex flex-wrap gap-2 mb-6 border-b border-gray-200 pb-2">
          {TABS.map(tab => (
            <button
              key={tab.id}
              onClick={() => setActiveTab(tab.id)}
              className={`px-4 py-2 rounded-t-lg font-medium transition-colors ${
                activeTab === tab.id 
                  ? 'bg-primary text-white' 
                  : 'text-gray-600 hover:bg-gray-100'
              }`}
            >
              {tab.label}
            </button>
          ))}
        </div>
        
        <div className="bg-white rounded-lg shadow p-6">
          {activeTab === 'products' && <ProductsManager />}
          {activeTab === 'categories' && <CategoriesManager />}
          {activeTab === 'subcategories' && <SubCategoriesManager />}
          {activeTab === 'brands' && <BrandsManager />}
          {activeTab === 'coupons' && <CouponsManager />}
          {activeTab === 'banners' && <BannersManager />}
          {activeTab === 'herobanners' && <HeroBannersManager />}
        </div>
      </div>
    </div>
  );
}

function ProductsManager() {
  const [mode, setMode] = useState<'list' | 'add'>('list');
  const [products, setProducts] = useState<Product[]>([]);
  const [categories, setCategories] = useState<Category[]>([]);
  const [subCategories, setSubCategories] = useState<SubCategory[]>([]);
  const [brands, setBrands] = useState<Brand[]>([]);

  useEffect(() => {
    fetchProducts();
    fetchCategories();
    fetchBrands();
  }, []);

  const fetchProducts = async () => {
        const res = await api.get<ApiResponse<any>>('/products');
        if (res.data.success) setProducts(res.data.data.products || []);
    };

    const fetchCategories = async () => {
        const res = await api.get<ApiResponse<Category[]>>('/categories');
        if (res.data.success) setCategories(res.data.data);
    };

    const fetchBrands = async () => {
        const res = await api.get<ApiResponse<Brand[]>>('/brands');
        if (res.data.success) setBrands(res.data.data);
    };

  return (
    <div>
      <div className="flex justify-between items-center mb-4">
        <h2 className="text-xl font-semibold text-gray-900">Products</h2>
        <button 
          onClick={() => setMode(mode === 'list' ? 'add' : 'list')}
          className="bg-primary text-white px-4 py-2 rounded-lg"
        >
          {mode === 'list' ? 'Add Product' : 'Back to List'}
        </button>
      </div>
      
      {mode === 'list' ? (
        <div className="overflow-x-auto">
          <table className="min-w-full divide-y divide-gray-200">
            <thead className="bg-gray-50">
              <tr>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Name</th>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Price</th>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Stock</th>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Status</th>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Actions</th>
              </tr>
            </thead>
            <tbody className="bg-white divide-y divide-gray-200">
              {products.map(product => (
                <tr key={product.productId}>
                  <td className="px-6 py-4 whitespace-nowrap">{product.productName}</td>
                  <td className="px-6 py-4 whitespace-nowrap">${product.price}</td>
                  <td className="px-6 py-4 whitespace-nowrap">{product.stock}</td>
                  <td className="px-6 py-4 whitespace-nowrap">{product.status}</td>
                  <td className="px-6 py-4 whitespace-nowrap">
                    <button className="text-blue-600 hover:text-blue-900 mr-2">Edit</button>
                    <button className="text-red-600 hover:text-red-900">Delete</button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      ) : (
        <AddProductForm 
          categories={categories} 
          subCategories={subCategories}
          brands={brands}
          onCategoryChange={(catId) => {
            api.get<ApiResponse<SubCategory[]>>(`/subcategories/category/${catId}`)
              .then(res => setSubCategories(res.data.success ? res.data.data : []));
          }}
          onSuccess={() => { setMode('list'); fetchProducts(); }}
        />
      )}
    </div>
  );
}

function AddProductForm({
  categories, subCategories, brands, onCategoryChange, onSuccess
}: {
  categories: Category[];
  subCategories: SubCategory[];
  brands: Brand[];
  onCategoryChange: (catId: number) => void;
  onSuccess: () => void;
}) {
  const [loading, setLoading] = useState(false);
  const [formData, setFormData] = useState({
    productName: '',
    description: '',
    categoryId: '',
    subCategoryId: '',
    brandId: '',
    sku: '',
    price: '',
    discountPrice: '',
    tax: 0,
    stock: '',
    weight: '',
    warranty: '',
    status: 'ACTIVE',
    featured: false,
  });
  const [images, setImages] = useState<File[]>([]);
  const [imagePreviews, setImagePreviews] = useState<string[]>([]);
  const [variants, setVariants] = useState<Partial<ProductVariant>[]>([]);
  const [showVariants, setShowVariants] = useState(false);

  const handleImageChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (e.target.files) {
      const newFiles = Array.from(e.target.files);
      setImages(prev => [...prev, ...newFiles]);
      const newPreviews = newFiles.map(file => URL.createObjectURL(file));
      setImagePreviews(prev => [...prev, ...newPreviews]);
    }
  };

  const removeImage = (index: number) => {
    setImages(images.filter((_, i) => i !== index));
    setImagePreviews(imagePreviews.filter((_, i) => i !== index));
  };

  const addVariant = () => {
    setVariants([...variants, {
      color: '',
      size: '',
      ram: '',
      storage: '',
      price: parseFloat(formData.price) || 0,
      stock: parseInt(formData.stock) || 0,
      sku: formData.sku || '',
      status: 'ACTIVE'
    }]);
  };

  const removeVariant = (index: number) => {
    setVariants(variants.filter((_, i) => i !== index));
  };

  const updateVariant = (index: number, field: keyof ProductVariant, value: any) => {
    const newVariants = [...variants];
    newVariants[index] = { ...newVariants[index], [field]: value };
    setVariants(newVariants);
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);
    try {
      const fd = new FormData();
      Object.entries(formData).forEach(([k, v]) => {
        if (v !== undefined && v !== null && v !== '') fd.append(k, v.toString());
      });
      images.forEach(img => fd.append('images', img));
      
      // Add variants
      if (variants.length > 0) {
        fd.append('variants', JSON.stringify(variants));
      }

      await api.post('/products/admin/create', fd, {
        headers: { 'Content-Type': 'multipart/form-data' }
      });
      onSuccess();
    } catch (err) {
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div>
      <form onSubmit={handleSubmit} className="grid grid-cols-1 lg:grid-cols-3 gap-6 mb-8">
        <div className="lg:col-span-2 space-y-4">
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-1">Product Name</label>
            <input 
              required
              type="text" 
              className="w-full border rounded-lg px-4 py-2"
              value={formData.productName}
              onChange={e => setFormData({...formData, productName: e.target.value})}
            />
          </div>
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-1">Description</label>
            <textarea 
              className="w-full border rounded-lg px-4 py-2"
              rows={4}
              value={formData.description}
              onChange={e => setFormData({...formData, description: e.target.value})}
            />
          </div>
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-1">Product Images</label>
            <div className="grid grid-cols-4 gap-4">
              <label className="aspect-[4/5] border-2 border-dashed border-gray-300 rounded-lg flex flex-col items-center justify-center cursor-pointer hover:border-primary">
                <span className="text-gray-500">Add Image</span>
                <input 
                  type="file" 
                  accept="image/*" 
                  multiple
                  onChange={handleImageChange}
                  className="hidden"
                />
              </label>
              {imagePreviews.map((preview, i) => (
                <div key={i} className="aspect-[4/5] bg-gray-100 rounded-lg relative group overflow-hidden">
                  <img src={preview} alt="" className="w-full h-full object-cover" />
                  <button 
                    type="button"
                    onClick={() => removeImage(i)} 
                    className="absolute top-2 right-2 bg-red-500 text-white w-6 h-6 rounded-full"
                  >×</button>
                </div>
              ))}
            </div>
          </div>
        </div>
        <div className="space-y-4">
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-1">Category</label>
            <select 
              className="w-full border rounded-lg px-4 py-2"
              value={formData.categoryId}
              onChange={e => {
                setFormData({...formData, categoryId: e.target.value, subCategoryId: ''});
                if (e.target.value) onCategoryChange(parseInt(e.target.value));
              }}
            >
              <option value="">Select</option>
              {categories.map(c => <option key={c.categoryId} value={c.categoryId}>{c.name}</option>)}
            </select>
          </div>
          {formData.categoryId && (
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Sub Category</label>
              <select 
                className="w-full border rounded-lg px-4 py-2"
                value={formData.subCategoryId}
                onChange={e => setFormData({...formData, subCategoryId: e.target.value})}
              >
                <option value="">Select</option>
                {subCategories.map(s => <option key={s.subCategoryId} value={s.subCategoryId}>{s.name}</option>)}
              </select>
            </div>
          )}
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-1">Brand</label>
            <select 
              className="w-full border rounded-lg px-4 py-2"
              value={formData.brandId}
              onChange={e => setFormData({...formData, brandId: e.target.value})}
            >
              <option value="">Select</option>
              {brands.map(b => <option key={b.brandId} value={b.brandId}>{b.brandName}</option>)}
            </select>
          </div>
          <div className="grid grid-cols-2 gap-4">
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Regular Price</label>
              <input 
                required
                type="number" 
                step="0.01"
                className="w-full border rounded-lg px-4 py-2"
                value={formData.price}
                onChange={e => setFormData({...formData, price: e.target.value})}
              />
            </div>
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Sale Price</label>
              <input 
                type="number" 
                step="0.01"
                className="w-full border rounded-lg px-4 py-2"
                value={formData.discountPrice}
                onChange={e => setFormData({...formData, discountPrice: e.target.value})}
              />
            </div>
          </div>
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-1">Stock</label>
            <input 
              required
              type="number" 
              className="w-full border rounded-lg px-4 py-2"
              value={formData.stock}
              onChange={e => setFormData({...formData, stock: e.target.value})}
            />
          </div>
          <div className="grid grid-cols-2 gap-4">
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">SKU</label>
              <input 
                type="text" 
                className="w-full border rounded-lg px-4 py-2"
                value={formData.sku}
                onChange={e => setFormData({...formData, sku: e.target.value})}
              />
            </div>
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Weight</label>
              <input 
                type="text" 
                className="w-full border rounded-lg px-4 py-2"
                value={formData.weight}
                onChange={e => setFormData({...formData, weight: e.target.value})}
              />
            </div>
          </div>
          <div className="flex items-center gap-4">
            <label className="flex items-center gap-2">
              <input 
                type="checkbox" 
                checked={formData.featured}
                onChange={e => setFormData({...formData, featured: e.target.checked})}
              />
              <span className="text-sm font-medium text-gray-700">Featured</span>
            </label>
            <label className="block text-sm font-medium text-gray-700">Status</label>
            <select 
              className="border rounded-lg px-2 py-1"
              value={formData.status}
              onChange={e => setFormData({...formData, status: e.target.value as 'ACTIVE' | 'INACTIVE'})}
            >
              <option value="ACTIVE">Active</option>
              <option value="INACTIVE">Inactive</option>
            </select>
          </div>
          <button 
            type="submit" 
            disabled={loading}
            className="w-full bg-primary text-white py-2 px-4 rounded-lg"
          >
            {loading ? 'Saving...' : 'Save Product'}
          </button>
        </div>
      </form>

      {/* Product Variants Section */}
      <div className="border-t border-gray-200 pt-6">
        <div className="flex items-center justify-between mb-4">
          <h3 className="text-lg font-semibold text-gray-800">Product Variants</h3>
          <button 
            type="button"
            onClick={() => setShowVariants(!showVariants)}
            className="text-primary hover:text-primary/80 font-medium"
          >
            {showVariants ? 'Hide Variants' : 'Add Variants'}
          </button>
        </div>
        
        {showVariants && (
          <div className="space-y-4">
            <button 
              type="button"
              onClick={addVariant}
              className="bg-secondary text-white px-4 py-2 rounded-lg"
            >
              Add New Variant
            </button>
            
            {variants.map((variant, index) => (
              <div key={index} className="bg-gray-50 p-4 rounded-lg border border-gray-200">
                <div className="flex justify-between items-start mb-3">
                  <h4 className="font-medium text-gray-700">Variant {index + 1}</h4>
                  <button 
                    type="button"
                    onClick={() => removeVariant(index)}
                    className="text-red-500 hover:text-red-700"
                  >
                    Remove
                  </button>
                </div>
                
                <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4">
                  <div>
                    <label className="block text-sm font-medium text-gray-600 mb-1">Color</label>
                    <input 
                      type="text" 
                      className="w-full border rounded-lg px-3 py-2"
                      value={variant.color || ''}
                      onChange={e => updateVariant(index, 'color', e.target.value)}
                      placeholder="e.g., Black, Blue"
                    />
                  </div>
                  <div>
                    <label className="block text-sm font-medium text-gray-600 mb-1">Size</label>
                    <input 
                      type="text" 
                      className="w-full border rounded-lg px-3 py-2"
                      value={variant.size || ''}
                      onChange={e => updateVariant(index, 'size', e.target.value)}
                      placeholder="e.g., S, M, L, XL"
                    />
                  </div>
                  <div>
                    <label className="block text-sm font-medium text-gray-600 mb-1">RAM</label>
                    <input 
                      type="text" 
                      className="w-full border rounded-lg px-3 py-2"
                      value={variant.ram || ''}
                      onChange={e => updateVariant(index, 'ram', e.target.value)}
                      placeholder="e.g., 4GB, 8GB, 16GB"
                    />
                  </div>
                  <div>
                    <label className="block text-sm font-medium text-gray-600 mb-1">Storage</label>
                    <input 
                      type="text" 
                      className="w-full border rounded-lg px-3 py-2"
                      value={variant.storage || ''}
                      onChange={e => updateVariant(index, 'storage', e.target.value)}
                      placeholder="e.g., 64GB, 128GB, 256GB"
                    />
                  </div>
                  <div>
                    <label className="block text-sm font-medium text-gray-600 mb-1">Price</label>
                    <input 
                      type="number" 
                      step="0.01"
                      className="w-full border rounded-lg px-3 py-2"
                      value={variant.price || ''}
                      onChange={e => updateVariant(index, 'price', parseFloat(e.target.value))}
                    />
                  </div>
                  <div>
                    <label className="block text-sm font-medium text-gray-600 mb-1">Stock</label>
                    <input 
                      type="number" 
                      className="w-full border rounded-lg px-3 py-2"
                      value={variant.stock || ''}
                      onChange={e => updateVariant(index, 'stock', parseInt(e.target.value))}
                    />
                  </div>
                  <div>
                    <label className="block text-sm font-medium text-gray-600 mb-1">SKU</label>
                    <input 
                      type="text" 
                      className="w-full border rounded-lg px-3 py-2"
                      value={variant.sku || ''}
                      onChange={e => updateVariant(index, 'sku', e.target.value)}
                    />
                  </div>
                  <div>
                    <label className="block text-sm font-medium text-gray-600 mb-1">Status</label>
                    <select 
                      className="w-full border rounded-lg px-3 py-2"
                      value={variant.status || 'ACTIVE'}
                      onChange={e => updateVariant(index, 'status', e.target.value)}
                    >
                      <option value="ACTIVE">Active</option>
                      <option value="INACTIVE">Inactive</option>
                    </select>
                  </div>
                </div>
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  );
}

function CategoriesManager() {
  const [mode, setMode] = useState<'list' | 'add' | 'edit'>('list');
  const [categories, setCategories] = useState<Category[]>([]);
  const [editing, setEditing] = useState<Category | null>(null);

  useEffect(() => {
    fetchCategories();
  }, []);

  const fetchCategories = async () => {
    const res = await api.get<ApiResponse<Category[]>>('/categories');
    if (res.data.success) setCategories(res.data.data);
  };

  const handleSave = async (data: Partial<Category>) => {
    try {
      if (mode === 'add') {
        await api.post('/categories/admin/create', {...data, slug: data.name?.toLowerCase().replace(/\s+/g, '-')});
      } else if (editing) {
        await api.put(`/categories/admin/${editing.categoryId}`, data);
      }
      setMode('list');
      fetchCategories();
    } catch (err) {
      console.error(err);
    }
  };

  const handleDelete = async (id: number) => {
    if (confirm('Are you sure?')) {
      await api.delete(`/categories/admin/${id}`);
      fetchCategories();
    }
  };

  return (
    <div>
      <div className="flex justify-between items-center mb-4">
        <h2 className="text-xl font-semibold text-gray-900">Categories</h2>
        <button 
          onClick={() => { setEditing(null); setMode('add'); }}
          className="bg-primary text-white px-4 py-2 rounded-lg"
        >
          Add Category
        </button>
      </div>
      {mode === 'list' ? (
        <div className="overflow-x-auto">
          <table className="min-w-full divide-y divide-gray-200">
            <thead className="bg-gray-50">
              <tr>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Name</th>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Slug</th>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Status</th>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Actions</th>
              </tr>
            </thead>
            <tbody className="bg-white divide-y divide-gray-200">
              {categories.map(c => (
                <tr key={c.categoryId}>
                  <td className="px-6 py-4 whitespace-nowrap">{c.name}</td>
                  <td className="px-6 py-4 whitespace-nowrap">{c.slug}</td>
                  <td className="px-6 py-4 whitespace-nowrap">{c.status}</td>
                  <td className="px-6 py-4 whitespace-nowrap">
                    <button onClick={() => { setEditing(c); setMode('edit'); }} className="text-blue-600 hover:text-blue-900 mr-2">Edit</button>
                    <button onClick={() => handleDelete(c.categoryId)} className="text-red-600 hover:text-red-900">Delete</button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      ) : (
        <CategoryForm 
          initialData={editing} 
          onSave={handleSave} 
          onCancel={() => setMode('list')} 
        />
      )}
    </div>
  );
}

function CategoryForm({
  initialData, onSave, onCancel
}: {
  initialData: Category | null;
  onSave: (data: Partial<Category>) => void;
  onCancel: () => void;
}) {
  const [name, setName] = useState(initialData?.name || '');
  const [slug, setSlug] = useState(initialData?.slug || '');
  const [image, setImage] = useState('');
  const [description, setDescription] = useState(initialData?.description || '');
  const [status, setStatus] = useState<'ACTIVE' | 'INACTIVE'>(initialData?.status || 'ACTIVE');

  return (
    <div className="max-w-2xl mx-auto">
      <div className="space-y-4">
        <div>
          <label className="block text-sm font-medium text-gray-700 mb-1">Name</label>
          <input 
            type="text" 
            className="w-full border rounded-lg px-4 py-2"
            value={name}
            onChange={e => {
              setName(e.target.value);
              setSlug(e.target.value.toLowerCase().replace(/\s+/g, '-'));
            }}
          />
        </div>
        <div>
          <label className="block text-sm font-medium text-gray-700 mb-1">Slug</label>
          <input 
            type="text" 
            className="w-full border rounded-lg px-4 py-2"
            value={slug}
            onChange={e => setSlug(e.target.value)}
          />
        </div>
        <div>
          <label className="block text-sm font-medium text-gray-700 mb-1">Image URL</label>
          <input 
            type="text" 
            className="w-full border rounded-lg px-4 py-2"
            value={image}
            onChange={e => setImage(e.target.value)}
          />
        </div>
        <div>
          <label className="block text-sm font-medium text-gray-700 mb-1">Description</label>
          <textarea 
            className="w-full border rounded-lg px-4 py-2"
            rows={3}
            value={description}
            onChange={e => setDescription(e.target.value)}
          />
        </div>
        <div>
          <label className="block text-sm font-medium text-gray-700 mb-1">Status</label>
          <select 
            className="w-full border rounded-lg px-4 py-2"
            value={status}
            onChange={e => setStatus(e.target.value as 'ACTIVE' | 'INACTIVE')}
          >
            <option value="ACTIVE">Active</option>
            <option value="INACTIVE">Inactive</option>
          </select>
        </div>
        <div className="flex gap-4 pt-4">
          <button 
            onClick={() => onSave({ name, slug, image, description, status })}
            className="bg-primary text-white px-6 py-2 rounded-lg"
          >
            Save
          </button>
          <button 
            onClick={onCancel}
            className="border border-gray-300 text-gray-700 px-6 py-2 rounded-lg"
          >
            Cancel
          </button>
        </div>
      </div>
    </div>
  );
}

function SubCategoriesManager() {
  const [mode, setMode] = useState<'list' | 'add' | 'edit'>('list');
  const [subCategories, setSubCategories] = useState<SubCategory[]>([]);
  const [categories, setCategories] = useState<Category[]>([]);
  const [editing, setEditing] = useState<SubCategory | null>(null);

  useEffect(() => {
    fetchSubCategories();
    fetchCategories();
  }, []);

  const fetchSubCategories = async () => {
    const res = await api.get<ApiResponse<SubCategory[]>>('/subcategories');
    if (res.data.success) setSubCategories(res.data.data);
  };

  const fetchCategories = async () => {
    const res = await api.get<ApiResponse<Category[]>>('/categories');
    if (res.data.success) setCategories(res.data.data);
  };

  const handleSave = async (data: Partial<SubCategory>) => {
    try {
      if (mode === 'add') {
        await api.post('/subcategories/admin/create', {...data, slug: data.name?.toLowerCase().replace(/\s+/g, '-')});
      } else if (editing) {
        await api.put(`/subcategories/admin/${editing.subCategoryId}`, data);
      }
      setMode('list');
      fetchSubCategories();
    } catch (err) {
      console.error(err);
    }
  };

  const handleDelete = async (id: number) => {
    if (confirm('Are you sure?')) {
      await api.delete(`/subcategories/admin/${id}`);
      fetchSubCategories();
    }
  };

  return (
    <div>
      <div className="flex justify-between items-center mb-4">
        <h2 className="text-xl font-semibold text-gray-900">Sub Categories</h2>
        <button 
          onClick={() => { setEditing(null); setMode('add'); }}
          className="bg-primary text-white px-4 py-2 rounded-lg"
        >
          Add Sub Category
        </button>
      </div>
      {mode === 'list' ? (
        <div className="overflow-x-auto">
          <table className="min-w-full divide-y divide-gray-200">
            <thead className="bg-gray-50">
              <tr>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Name</th>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Category</th>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Status</th>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Actions</th>
              </tr>
            </thead>
            <tbody className="bg-white divide-y divide-gray-200">
              {subCategories.map(s => (
                <tr key={s.subCategoryId}>
                  <td className="px-6 py-4 whitespace-nowrap">{s.name}</td>
                  <td className="px-6 py-4 whitespace-nowrap">{categories.find(c => c.categoryId === s.categoryId)?.name}</td>
                  <td className="px-6 py-4 whitespace-nowrap">{s.status}</td>
                  <td className="px-6 py-4 whitespace-nowrap">
                    <button onClick={() => { setEditing(s); setMode('edit'); }} className="text-blue-600 hover:text-blue-900 mr-2">Edit</button>
                    <button onClick={() => handleDelete(s.subCategoryId)} className="text-red-600 hover:text-red-900">Delete</button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      ) : (
        <SubCategoryForm 
          initialData={editing} 
          categories={categories}
          onSave={handleSave} 
          onCancel={() => setMode('list')} 
        />
      )}
    </div>
  );
}

function SubCategoryForm({
  initialData, categories, onSave, onCancel
}: {
  initialData: SubCategory | null;
  categories: Category[];
  onSave: (data: Partial<SubCategory>) => void;
  onCancel: () => void;
}) {
  const [name, setName] = useState(initialData?.name || '');
  const [slug, setSlug] = useState(initialData?.slug || '');
  const [categoryId, setCategoryId] = useState<string>(initialData?.categoryId?.toString() || '');
  const [image, setImage] = useState(initialData?.image || '');
  const [description, setDescription] = useState(initialData?.description || '');
  const [status, setStatus] = useState<'ACTIVE' | 'INACTIVE'>(initialData?.status || 'ACTIVE');

  return (
    <div className="max-w-2xl mx-auto">
      <div className="space-y-4">
        <div>
          <label className="block text-sm font-medium text-gray-700 mb-1">Category</label>
          <select 
            className="w-full border rounded-lg px-4 py-2"
            value={categoryId}
            onChange={e => setCategoryId(e.target.value)}
          >
            <option value="">Select</option>
            {categories.map(c => <option key={c.categoryId} value={c.categoryId}>{c.name}</option>)}
          </select>
        </div>
        <div>
          <label className="block text-sm font-medium text-gray-700 mb-1">Name</label>
          <input 
            type="text" 
            className="w-full border rounded-lg px-4 py-2"
            value={name}
            onChange={e => {
              setName(e.target.value);
              setSlug(e.target.value.toLowerCase().replace(/\s+/g, '-'));
            }}
          />
        </div>
        <div>
          <label className="block text-sm font-medium text-gray-700 mb-1">Slug</label>
          <input 
            type="text" 
            className="w-full border rounded-lg px-4 py-2"
            value={slug}
            onChange={e => setSlug(e.target.value)}
          />
        </div>
        <div>
          <label className="block text-sm font-medium text-gray-700 mb-1">Image URL</label>
          <input 
            type="text" 
            className="w-full border rounded-lg px-4 py-2"
            value={image}
            onChange={e => setImage(e.target.value)}
          />
        </div>
        <div>
          <label className="block text-sm font-medium text-gray-700 mb-1">Description</label>
          <textarea 
            className="w-full border rounded-lg px-4 py-2"
            rows={3}
            value={description}
            onChange={e => setDescription(e.target.value)}
          />
        </div>
        <div>
          <label className="block text-sm font-medium text-gray-700 mb-1">Status</label>
          <select 
            className="w-full border rounded-lg px-4 py-2"
            value={status}
            onChange={e => setStatus(e.target.value as 'ACTIVE' | 'INACTIVE')}
          >
            <option value="ACTIVE">Active</option>
            <option value="INACTIVE">Inactive</option>
          </select>
        </div>
        <div className="flex gap-4 pt-4">
          <button 
            onClick={() => onSave({ name, slug, categoryId: parseInt(categoryId), image, description, status })}
            className="bg-primary text-white px-6 py-2 rounded-lg"
          >
            Save
          </button>
          <button 
            onClick={onCancel}
            className="border border-gray-300 text-gray-700 px-6 py-2 rounded-lg"
          >
            Cancel
          </button>
        </div>
      </div>
    </div>
  );
}

function BrandsManager() {
  const [mode, setMode] = useState<'list' | 'add' | 'edit'>('list');
  const [brands, setBrands] = useState<Brand[]>([]);
  const [editing, setEditing] = useState<Brand | null>(null);

  useEffect(() => {
    fetchBrands();
  }, []);

  const fetchBrands = async () => {
    const res = await api.get<ApiResponse<Brand[]>>('/brands');
    if (res.data.success) setBrands(res.data.data);
  };

  const handleSave = async (data: Partial<Brand>) => {
    try {
      if (mode === 'add') {
        await api.post('/brands/admin/create', data);
      } else if (editing) {
        await api.put(`/brands/admin/${editing.brandId}`, data);
      }
      setMode('list');
      fetchBrands();
    } catch (err) {
      console.error(err);
    }
  };

  const handleDelete = async (id: number) => {
    if (confirm('Are you sure?')) {
      await api.delete(`/brands/admin/${id}`);
      fetchBrands();
    }
  };

  return (
    <div>
      <div className="flex justify-between items-center mb-4">
        <h2 className="text-xl font-semibold text-gray-900">Brands</h2>
        <button 
          onClick={() => { setEditing(null); setMode('add'); }}
          className="bg-primary text-white px-4 py-2 rounded-lg"
        >
          Add Brand
        </button>
      </div>
      {mode === 'list' ? (
        <div className="overflow-x-auto">
          <table className="min-w-full divide-y divide-gray-200">
            <thead className="bg-gray-50">
              <tr>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Name</th>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Status</th>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Actions</th>
              </tr>
            </thead>
            <tbody className="bg-white divide-y divide-gray-200">
              {brands.map(b => (
                <tr key={b.brandId}>
                  <td className="px-6 py-4 whitespace-nowrap">{b.brandName}</td>
                  <td className="px-6 py-4 whitespace-nowrap">{b.status}</td>
                  <td className="px-6 py-4 whitespace-nowrap">
                    <button onClick={() => { setEditing(b); setMode('edit'); }} className="text-blue-600 hover:text-blue-900 mr-2">Edit</button>
                    <button onClick={() => handleDelete(b.brandId)} className="text-red-600 hover:text-red-900">Delete</button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      ) : (
        <BrandForm 
          initialData={editing} 
          onSave={handleSave} 
          onCancel={() => setMode('list')} 
        />
      )}
    </div>
  );
}

function BrandForm({
  initialData, onSave, onCancel
}: {
  initialData: Brand | null;
  onSave: (data: Partial<Brand>) => void;
  onCancel: () => void;
}) {
  const [brandName, setBrandName] = useState(initialData?.brandName || '');
  const [brandLogo, setBrandLogo] = useState(initialData?.brandLogo || '');
  const [description, setDescription] = useState(initialData?.description || '');
  const [status, setStatus] = useState<'ACTIVE' | 'INACTIVE'>(initialData?.status || 'ACTIVE');

  return (
    <div className="max-w-2xl mx-auto">
      <div className="space-y-4">
        <div>
          <label className="block text-sm font-medium text-gray-700 mb-1">Brand Name</label>
          <input 
            type="text" 
            className="w-full border rounded-lg px-4 py-2"
            value={brandName}
            onChange={e => setBrandName(e.target.value)}
          />
        </div>
        <div>
          <label className="block text-sm font-medium text-gray-700 mb-1">Logo URL</label>
          <input 
            type="text" 
            className="w-full border rounded-lg px-4 py-2"
            value={brandLogo}
            onChange={e => setBrandLogo(e.target.value)}
          />
        </div>
        <div>
          <label className="block text-sm font-medium text-gray-700 mb-1">Description</label>
          <textarea 
            className="w-full border rounded-lg px-4 py-2"
            rows={3}
            value={description}
            onChange={e => setDescription(e.target.value)}
          />
        </div>
        <div>
          <label className="block text-sm font-medium text-gray-700 mb-1">Status</label>
          <select 
            className="w-full border rounded-lg px-4 py-2"
            value={status}
            onChange={e => setStatus(e.target.value as 'ACTIVE' | 'INACTIVE')}
          >
            <option value="ACTIVE">Active</option>
            <option value="INACTIVE">Inactive</option>
          </select>
        </div>
        <div className="flex gap-4 pt-4">
          <button 
            onClick={() => onSave({ brandName, brandLogo, description, status })}
            className="bg-primary text-white px-6 py-2 rounded-lg"
          >
            Save
          </button>
          <button 
            onClick={onCancel}
            className="border border-gray-300 text-gray-700 px-6 py-2 rounded-lg"
          >
            Cancel
          </button>
        </div>
      </div>
    </div>
  );
}

function CouponsManager() {
  const [mode, setMode] = useState<'list' | 'add' | 'edit'>('list');
  const [coupons, setCoupons] = useState<Coupon[]>([]);
  const [editing, setEditing] = useState<Coupon | null>(null);

  useEffect(() => {
    fetchCoupons();
  }, []);

  const fetchCoupons = async () => {
    const res = await api.get<ApiResponse<Coupon[]>>('/coupons');
    if (res.data.success) setCoupons(res.data.data);
  };

  const handleSave = async (data: Partial<Coupon>) => {
    try {
      if (mode === 'add') {
        await api.post('/coupons/admin/create', data);
      } else if (editing) {
        await api.put(`/coupons/admin/${editing.couponId}`, data);
      }
      setMode('list');
      fetchCoupons();
    } catch (err) {
      console.error(err);
    }
  };

  const handleDelete = async (id: number) => {
    if (confirm('Are you sure?')) {
      await api.delete(`/coupons/admin/${id}`);
      fetchCoupons();
    }
  };

  return (
    <div>
      <div className="flex justify-between items-center mb-4">
        <h2 className="text-xl font-semibold text-gray-900">Coupons</h2>
        <button 
          onClick={() => { setEditing(null); setMode('add'); }}
          className="bg-primary text-white px-4 py-2 rounded-lg"
        >
          Add Coupon
        </button>
      </div>
      {mode === 'list' ? (
        <div className="overflow-x-auto">
          <table className="min-w-full divide-y divide-gray-200">
            <thead className="bg-gray-50">
              <tr>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Code</th>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Type</th>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Value</th>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Expiry</th>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Actions</th>
              </tr>
            </thead>
            <tbody className="bg-white divide-y divide-gray-200">
              {coupons.map(c => (
                <tr key={c.couponId}>
                  <td className="px-6 py-4 whitespace-nowrap">{c.couponCode}</td>
                  <td className="px-6 py-4 whitespace-nowrap">{c.discountType}</td>
                  <td className="px-6 py-4 whitespace-nowrap">{c.discountValue}</td>
                  <td className="px-6 py-4 whitespace-nowrap">{c.expiryDate}</td>
                  <td className="px-6 py-4 whitespace-nowrap">
                    <button onClick={() => { setEditing(c); setMode('edit'); }} className="text-blue-600 hover:text-blue-900 mr-2">Edit</button>
                    <button onClick={() => handleDelete(c.couponId)} className="text-red-600 hover:text-red-900">Delete</button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      ) : (
        <CouponForm 
          initialData={editing} 
          onSave={handleSave} 
          onCancel={() => setMode('list')} 
        />
      )}
    </div>
  );
}

function CouponForm({
  initialData, onSave, onCancel
}: {
  initialData: Coupon | null;
  onSave: (data: Partial<Coupon>) => void;
  onCancel: () => void;
}) {
  const [couponCode, setCouponCode] = useState(initialData?.couponCode || '');
  const [discountType, setDiscountType] = useState<'PERCENTAGE' | 'FIXED'>(initialData?.discountType || 'PERCENTAGE');
  const [discountValue, setDiscountValue] = useState(initialData?.discountValue?.toString() || '');
  const [minimumAmount, setMinimumAmount] = useState(initialData?.minimumAmount?.toString() || '');
  const [maximumDiscount, setMaximumDiscount] = useState(initialData?.maximumDiscount?.toString() || '');
  const [expiryDate, setExpiryDate] = useState(initialData?.expiryDate?.split('T')[0] || '');
  const [usageLimit, setUsageLimit] = useState(initialData?.usageLimit?.toString() || '');
  const [status, setStatus] = useState<'ACTIVE' | 'INACTIVE'>(initialData?.status || 'ACTIVE');

  return (
    <div className="max-w-2xl mx-auto">
      <div className="space-y-4">
        <div>
          <label className="block text-sm font-medium text-gray-700 mb-1">Coupon Code</label>
          <input 
            type="text" 
            className="w-full border rounded-lg px-4 py-2"
            value={couponCode}
            onChange={e => setCouponCode(e.target.value)}
          />
        </div>
        <div>
          <label className="block text-sm font-medium text-gray-700 mb-1">Discount Type</label>
          <select 
            className="w-full border rounded-lg px-4 py-2"
            value={discountType}
            onChange={e => setDiscountType(e.target.value as 'PERCENTAGE' | 'FIXED')}
          >
            <option value="PERCENTAGE">Percentage</option>
            <option value="FIXED">Fixed Amount</option>
          </select>
        </div>
        <div>
          <label className="block text-sm font-medium text-gray-700 mb-1">Discount Value</label>
          <input 
            type="number" 
            step="0.01"
            className="w-full border rounded-lg px-4 py-2"
            value={discountValue}
            onChange={e => setDiscountValue(e.target.value)}
          />
        </div>
        <div className="grid grid-cols-2 gap-4">
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-1">Minimum Amount</label>
            <input 
              type="number" 
              step="0.01"
              className="w-full border rounded-lg px-4 py-2"
              value={minimumAmount}
              onChange={e => setMinimumAmount(e.target.value)}
            />
          </div>
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-1">Maximum Discount</label>
            <input 
              type="number" 
              step="0.01"
              className="w-full border rounded-lg px-4 py-2"
              value={maximumDiscount}
              onChange={e => setMaximumDiscount(e.target.value)}
            />
          </div>
        </div>
        <div>
          <label className="block text-sm font-medium text-gray-700 mb-1">Expiry Date</label>
          <input 
            type="date" 
            className="w-full border rounded-lg px-4 py-2"
            value={expiryDate}
            onChange={e => setExpiryDate(e.target.value)}
          />
        </div>
        <div>
          <label className="block text-sm font-medium text-gray-700 mb-1">Usage Limit</label>
          <input 
            type="number" 
            className="w-full border rounded-lg px-4 py-2"
            value={usageLimit}
            onChange={e => setUsageLimit(e.target.value)}
          />
        </div>
        <div>
          <label className="block text-sm font-medium text-gray-700 mb-1">Status</label>
          <select 
            className="w-full border rounded-lg px-4 py-2"
            value={status}
            onChange={e => setStatus(e.target.value as 'ACTIVE' | 'INACTIVE')}
          >
            <option value="ACTIVE">Active</option>
            <option value="INACTIVE">Inactive</option>
          </select>
        </div>
        <div className="flex gap-4 pt-4">
          <button 
            onClick={() => onSave({ 
              couponCode, 
              discountType, 
              discountValue: parseFloat(discountValue), 
              minimumAmount: parseFloat(minimumAmount), 
              maximumDiscount: parseFloat(maximumDiscount), 
              expiryDate, 
              usageLimit: parseInt(usageLimit), 
              status 
            })}
            className="bg-primary text-white px-6 py-2 rounded-lg"
          >
            Save
          </button>
          <button 
            onClick={onCancel}
            className="border border-gray-300 text-gray-700 px-6 py-2 rounded-lg"
          >
            Cancel
          </button>
        </div>
      </div>
    </div>
  );
}

function BannersManager() {
  const [mode, setMode] = useState<'list' | 'add' | 'edit'>('list');
  const [banners, setBanners] = useState<Banner[]>([]);
  const [editing, setEditing] = useState<Banner | null>(null);

  useEffect(() => {
    fetchBanners();
  }, []);

  const fetchBanners = async () => {
    const res = await api.get<ApiResponse<Banner[]>>('/banners');
    if (res.data.success) setBanners(res.data.data);
  };

  const handleSave = async (data: Partial<Banner>) => {
    try {
      if (mode === 'add') {
        await api.post('/banners/admin/create', data);
      } else if (editing) {
        await api.put(`/banners/admin/${editing.bannerId}`, data);
      }
      setMode('list');
      fetchBanners();
    } catch (err) {
      console.error(err);
    }
  };

  const handleDelete = async (id: number) => {
    if (confirm('Are you sure?')) {
      await api.delete(`/banners/admin/${id}`);
      fetchBanners();
    }
  };

  return (
    <div>
      <div className="flex justify-between items-center mb-4">
        <h2 className="text-xl font-semibold text-gray-900">Banners</h2>
        <button 
          onClick={() => { setEditing(null); setMode('add'); }}
          className="bg-primary text-white px-4 py-2 rounded-lg"
        >
          Add Banner
        </button>
      </div>
      {mode === 'list' ? (
        <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
          {banners.map(b => (
            <div key={b.bannerId} className="border rounded-lg overflow-hidden">
              {b.imageUrl && <img src={b.imageUrl} alt="" className="w-full h-40 object-cover" />}
              <div className="p-4">
                <h3 className="font-semibold">{b.title}</h3>
                <div className="flex gap-2 mt-2">
                  <button onClick={() => { setEditing(b); setMode('edit'); }} className="text-blue-600 hover:text-blue-900">Edit</button>
                  <button onClick={() => handleDelete(b.bannerId)} className="text-red-600 hover:text-red-900">Delete</button>
                </div>
              </div>
            </div>
          ))}
        </div>
      ) : (
        <BannerForm 
          initialData={editing} 
          onSave={handleSave} 
          onCancel={() => setMode('list')} 
        />
      )}
    </div>
  );
}

function BannerForm({
  initialData, onSave, onCancel
}: {
  initialData: Banner | null;
  onSave: (data: Partial<Banner>) => void;
  onCancel: () => void;
}) {
  const [title, setTitle] = useState(initialData?.title || '');
  const [imageUrl, setImageUrl] = useState(initialData?.imageUrl || '');
  const [redirectUrl, setRedirectUrl] = useState(initialData?.redirectUrl || '');
  const [displayOrder, setDisplayOrder] = useState(initialData?.displayOrder?.toString() || '');
  const [status, setStatus] = useState<'ACTIVE' | 'INACTIVE'>(initialData?.status || 'ACTIVE');

  return (
    <div className="max-w-2xl mx-auto">
      <div className="space-y-4">
        <div>
          <label className="block text-sm font-medium text-gray-700 mb-1">Title</label>
          <input 
            type="text" 
            className="w-full border rounded-lg px-4 py-2"
            value={title}
            onChange={e => setTitle(e.target.value)}
          />
        </div>
        <div>
          <label className="block text-sm font-medium text-gray-700 mb-1">Image URL</label>
          <input 
            type="text" 
            className="w-full border rounded-lg px-4 py-2"
            value={imageUrl}
            onChange={e => setImageUrl(e.target.value)}
          />
        </div>
        <div>
          <label className="block text-sm font-medium text-gray-700 mb-1">Redirect URL</label>
          <input 
            type="text" 
            className="w-full border rounded-lg px-4 py-2"
            value={redirectUrl}
            onChange={e => setRedirectUrl(e.target.value)}
          />
        </div>
        <div>
          <label className="block text-sm font-medium text-gray-700 mb-1">Display Order</label>
          <input 
            type="number" 
            className="w-full border rounded-lg px-4 py-2"
            value={displayOrder}
            onChange={e => setDisplayOrder(e.target.value)}
          />
        </div>
        <div>
          <label className="block text-sm font-medium text-gray-700 mb-1">Status</label>
          <select 
            className="w-full border rounded-lg px-4 py-2"
            value={status}
            onChange={e => setStatus(e.target.value as 'ACTIVE' | 'INACTIVE')}
          >
            <option value="ACTIVE">Active</option>
            <option value="INACTIVE">Inactive</option>
          </select>
        </div>
        <div className="flex gap-4 pt-4">
          <button 
            onClick={() => onSave({ 
              title, 
              imageUrl, 
              redirectUrl, 
              displayOrder: parseInt(displayOrder), 
              status 
            })}
            className="bg-primary text-white px-6 py-2 rounded-lg"
          >
            Save
          </button>
          <button 
            onClick={onCancel}
            className="border border-gray-300 text-gray-700 px-6 py-2 rounded-lg"
          >
            Cancel
          </button>
        </div>
      </div>
    </div>
  );
}

function HeroBannersManager() {
  const [mode, setMode] = useState<'list' | 'add' | 'edit'>('list');
  const [heroBanners, setHeroBanners] = useState<HeroBanner[]>([]);
  const [editing, setEditing] = useState<HeroBanner | null>(null);

  useEffect(() => {
    fetchHeroBanners();
  }, []);

  const fetchHeroBanners = async () => {
    const res = await api.get<ApiResponse<HeroBanner[]>>('/hero-banners');
    if (res.data.success) setHeroBanners(res.data.data);
  };

  const handleSave = async (data: Partial<HeroBanner>) => {
    try {
      if (mode === 'add') {
        await api.post('/hero-banners/admin/create', data);
      } else if (editing) {
        await api.put(`/hero-banners/admin/${editing.heroId}`, data);
      }
      setMode('list');
      fetchHeroBanners();
    } catch (err) {
      console.error(err);
    }
  };

  const handleDelete = async (id: number) => {
    if (confirm('Are you sure?')) {
      await api.delete(`/hero-banners/admin/${id}`);
      fetchHeroBanners();
    }
  };

  return (
    <div>
      <div className="flex justify-between items-center mb-4">
        <h2 className="text-xl font-semibold text-gray-900">Hero Banners</h2>
        <button 
          onClick={() => { setEditing(null); setMode('add'); }}
          className="bg-primary text-white px-4 py-2 rounded-lg"
        >
          Add Hero Banner
        </button>
      </div>
      {mode === 'list' ? (
        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
          {heroBanners.map(h => (
            <div key={h.heroId} className="border rounded-lg overflow-hidden">
              {h.imageUrl && <img src={h.imageUrl} alt="" className="w-full h-48 object-cover" />}
              <div className="p-4">
                <h3 className="font-semibold text-xl">{h.title}</h3>
                {h.subtitle && <p className="text-gray-600">{h.subtitle}</p>}
                <div className="flex gap-2 mt-3">
                  <button onClick={() => { setEditing(h); setMode('edit'); }} className="text-blue-600 hover:text-blue-900">Edit</button>
                  <button onClick={() => handleDelete(h.heroId)} className="text-red-600 hover:text-red-900">Delete</button>
                </div>
              </div>
            </div>
          ))}
        </div>
      ) : (
        <HeroBannerForm 
          initialData={editing} 
          onSave={handleSave} 
          onCancel={() => setMode('list')} 
        />
      )}
    </div>
  );
}

function HeroBannerForm({
  initialData, onSave, onCancel
}: {
  initialData: HeroBanner | null;
  onSave: (data: Partial<HeroBanner>) => void;
  onCancel: () => void;
}) {
  const [title, setTitle] = useState(initialData?.title || '');
  const [subtitle, setSubtitle] = useState(initialData?.subtitle || '');
  const [imageUrl, setImageUrl] = useState(initialData?.imageUrl || '');
  const [buttonText, setButtonText] = useState(initialData?.buttonText || '');
  const [buttonLink, setButtonLink] = useState(initialData?.buttonLink || '');
  const [displayOrder, setDisplayOrder] = useState(initialData?.displayOrder?.toString() || '');
  const [status, setStatus] = useState<'ACTIVE' | 'INACTIVE'>(initialData?.status || 'ACTIVE');

  return (
    <div className="max-w-2xl mx-auto">
      <div className="space-y-4">
        <div>
          <label className="block text-sm font-medium text-gray-700 mb-1">Title</label>
          <input 
            type="text" 
            className="w-full border rounded-lg px-4 py-2"
            value={title}
            onChange={e => setTitle(e.target.value)}
          />
        </div>
        <div>
          <label className="block text-sm font-medium text-gray-700 mb-1">Subtitle</label>
          <input 
            type="text" 
            className="w-full border rounded-lg px-4 py-2"
            value={subtitle}
            onChange={e => setSubtitle(e.target.value)}
          />
        </div>
        <div>
          <label className="block text-sm font-medium text-gray-700 mb-1">Image URL</label>
          <input 
            type="text" 
            className="w-full border rounded-lg px-4 py-2"
            value={imageUrl}
            onChange={e => setImageUrl(e.target.value)}
          />
        </div>
        <div>
          <label className="block text-sm font-medium text-gray-700 mb-1">Button Text</label>
          <input 
            type="text" 
            className="w-full border rounded-lg px-4 py-2"
            value={buttonText}
            onChange={e => setButtonText(e.target.value)}
          />
        </div>
        <div>
          <label className="block text-sm font-medium text-gray-700 mb-1">Button Link</label>
          <input 
            type="text" 
            className="w-full border rounded-lg px-4 py-2"
            value={buttonLink}
            onChange={e => setButtonLink(e.target.value)}
          />
        </div>
        <div>
          <label className="block text-sm font-medium text-gray-700 mb-1">Display Order</label>
          <input 
            type="number" 
            className="w-full border rounded-lg px-4 py-2"
            value={displayOrder}
            onChange={e => setDisplayOrder(e.target.value)}
          />
        </div>
        <div>
          <label className="block text-sm font-medium text-gray-700 mb-1">Status</label>
          <select 
            className="w-full border rounded-lg px-4 py-2"
            value={status}
            onChange={e => setStatus(e.target.value as 'ACTIVE' | 'INACTIVE')}
          >
            <option value="ACTIVE">Active</option>
            <option value="INACTIVE">Inactive</option>
          </select>
        </div>
        <div className="flex gap-4 pt-4">
          <button 
            onClick={() => onSave({ 
              title, 
              subtitle,
              imageUrl, 
              buttonText,
              buttonLink,
              displayOrder: parseInt(displayOrder), 
              status 
            })}
            className="bg-primary text-white px-6 py-2 rounded-lg"
          >
            Save
          </button>
          <button 
            onClick={onCancel}
            className="border border-gray-300 text-gray-700 px-6 py-2 rounded-lg"
          >
            Cancel
          </button>
        </div>
      </div>
    </div>
  );
}
