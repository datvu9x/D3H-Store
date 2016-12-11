package dev.datvt.clothingstored3h.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import dev.datvt.clothingstored3h.R;
import dev.datvt.clothingstored3h.adapters.ProductAdapter;
import dev.datvt.clothingstored3h.models.Product;
import dev.datvt.clothingstored3h.utils.DatabaseHandler;

/**
 * Created by DatVIT on 10/16/2016.
 */

public class FragmentSearchProduct extends Fragment {

    private View viewFragment;
    private Spinner spPhanLoai;
    private List<String> arrLoaiKH;
    private DatabaseHandler databaseHandler;
    private List<String> arrayListProduct;
    private List<Product> arrayList;
    private ProductAdapter productAdapter;
    private ListView listView;
    private ImageView btnSearch, btnDel;
    private SwipeRefreshLayout ref;
    private AutoCompleteTextView etSearch;
    private ArrayAdapter adapterSearch;
    public int pos = 0;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseHandler = new DatabaseHandler(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        viewFragment = inflater.inflate(R.layout.fragment_search_product, container, false);
        spPhanLoai = (Spinner) viewFragment.findViewById(R.id.spPhanLoai);

        arrLoaiKH = new ArrayList<>();
        arrLoaiKH.add("Theo tên sản phẩm");
        arrLoaiKH.add("Theo đối tượng");
        arrLoaiKH.add("Theo màu sắc");
        arrLoaiKH.add("Theo size");
        arrLoaiKH.add("Theo loại");
        arrLoaiKH.add("Theo đơn giá");
        arrLoaiKH.add("Theo mùa");
        arrLoaiKH.add("Theo giảm giá");
        arrLoaiKH.add("Theo NSX");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arrLoaiKH);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spPhanLoai.setAdapter(arrayAdapter);

        spPhanLoai.setSelection(0);

        etSearch = (AutoCompleteTextView) viewFragment.findViewById(R.id.etSearch);
        btnDel = (ImageView) viewFragment.findViewById(R.id.imgDelete);
        listView = (ListView) viewFragment.findViewById(R.id.listProductSearch);
        btnSearch = (ImageView) viewFragment.findViewById(R.id.btnSearch);

        arrayListProduct = new ArrayList<>();
        if (databaseHandler.getListName().size() > 0) {
            arrayListProduct = databaseHandler.getListName();
            Log.e("DATA_SIZE", arrayListProduct.size() + "");
        }
        adapterSearch = new ArrayAdapter(getActivity(), android.R.layout.simple_dropdown_item_1line, arrayListProduct);
        etSearch.setAdapter(adapterSearch);

        ref = (SwipeRefreshLayout) viewFragment.findViewById(R.id.swipeRefreshLayout);
        ref.setColorSchemeColors(getResources().getColor(R.color.colorOrange),
                getResources().getColor(R.color.colorOrange),
                getResources().getColor(R.color.colorOrange));

        ref.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new GetAllProduct().execute();

            }
        });

        new GetAllProduct().execute();

        spPhanLoai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                onSelect(position);
                pos = position;
                etSearch.setText("");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etSearch.setText("");
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = etSearch.getText().toString().trim();
                if (key != null && !key.isEmpty()) {
                    new GetProductByParams().execute(key);
                } else {
                    Toast.makeText(getActivity(), "Bạn chưa nhập từ khóa tìm kiếm", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return viewFragment;
    }

    private void onSelect(int pos) {
        switch (pos) {
            case 0:
                etSearch.setHint("Nhập tên sản phẩm (VD: Áo sơ mi...)");
                if (arrayListProduct.size() > 0) {
                    arrayListProduct.clear();
                }
                if (databaseHandler.getListName().size() > 0) {
                    arrayListProduct = databaseHandler.getListName();
                }
                adapterSearch = new ArrayAdapter(getActivity(), android.R.layout.simple_dropdown_item_1line, arrayListProduct);
                etSearch.setAdapter(adapterSearch);
                break;
            case 1:
                etSearch.setHint("Nhập đối tượng sử dụng (VD: Nam, Nữ...)");
                if (arrayListProduct.size() > 0) {
                    arrayListProduct.clear();
                }
                if (databaseHandler.getListObject().size() > 0) {
                    arrayListProduct = databaseHandler.getListObject();
                }
                adapterSearch = new ArrayAdapter(getActivity(), android.R.layout.simple_dropdown_item_1line, arrayListProduct);
                etSearch.setAdapter(adapterSearch);
                break;
            case 2:
                etSearch.setHint("Nhập tên màu sắc (VD: Đỏ, Đen...)");
                if (arrayListProduct.size() > 0) {
                    arrayListProduct.clear();
                }
                if (databaseHandler.getListMauSac().size() > 0) {
                    arrayListProduct = databaseHandler.getListMauSac();
                }
                adapterSearch = new ArrayAdapter(getActivity(), android.R.layout.simple_dropdown_item_1line, arrayListProduct);
                etSearch.setAdapter(adapterSearch);
                break;
            case 3:
                etSearch.setHint("Nhập kích cỡ sản phẩm (VD: 32, 35..)");
                if (arrayListProduct.size() > 0) {
                    arrayListProduct.clear();
                }
                if (databaseHandler.getListSize().size() > 0) {
                    arrayListProduct = databaseHandler.getListSize();
                }
                adapterSearch = new ArrayAdapter(getActivity(), android.R.layout.simple_dropdown_item_1line, arrayListProduct);
                etSearch.setAdapter(adapterSearch);
                break;
            case 4:
                etSearch.setHint("Nhập tên loại sản phẩm (VD: Quần, Áo...)");
                if (arrayListProduct.size() > 0) {
                    arrayListProduct.clear();
                }
                if (databaseHandler.getListLoai().size() > 0) {
                    arrayListProduct = databaseHandler.getListLoai();
                }
                adapterSearch = new ArrayAdapter(getActivity(), android.R.layout.simple_dropdown_item_1line, arrayListProduct);
                etSearch.setAdapter(adapterSearch);
                break;
            case 5:
                etSearch.setHint("Nhập đơn giá của sản phẩm (VD: 150000, 250000...)");
                if (arrayListProduct.size() > 0) {
                    arrayListProduct.clear();
                }
                if (databaseHandler.getListDonGia().size() > 0) {
                    arrayListProduct = databaseHandler.getListDonGia();
                }
                adapterSearch = new ArrayAdapter(getActivity(), android.R.layout.simple_dropdown_item_1line, arrayListProduct);
                etSearch.setAdapter(adapterSearch);
                break;
            case 6:
                etSearch.setHint("Nhập tên mùa (VD: Xuân, Hè...)");
                if (arrayListProduct.size() > 0) {
                    arrayListProduct.clear();
                }
                if (databaseHandler.getListMua().size() > 0) {
                    arrayListProduct = databaseHandler.getListMua();
                }
                adapterSearch = new ArrayAdapter(getActivity(), android.R.layout.simple_dropdown_item_1line, arrayListProduct);
                etSearch.setAdapter(adapterSearch);
                break;
            case 7:
                etSearch.setHint("Nhập phần trăm giảm giá (VD: 10, 5...)");
                if (arrayListProduct.size() > 0) {
                    arrayListProduct.clear();
                }
                if (databaseHandler.getListGiamGia().size() > 0) {
                    arrayListProduct = databaseHandler.getListGiamGia();
                }
                adapterSearch = new ArrayAdapter(getActivity(), android.R.layout.simple_dropdown_item_1line, arrayListProduct);
                etSearch.setAdapter(adapterSearch);
                break;
            case 8:
                etSearch.setHint("Nhập tên nhà sản xuất (VD: D3H...)");
                if (arrayListProduct.size() > 0) {
                    arrayListProduct.clear();
                }
                if (databaseHandler.getListNSX().size() > 0) {
                    arrayListProduct = databaseHandler.getListNSX();
                }
                adapterSearch = new ArrayAdapter(getActivity(), android.R.layout.simple_dropdown_item_1line, arrayListProduct);
                etSearch.setAdapter(adapterSearch);
                break;
        }
    }

//    private void search(String key, int pos) {
//        switch (pos) {
//            case 0:
//                if (key != null && !key.isEmpty()) {
//                    new GetProductByName().execute(key);
//                } else {
//                    Toast.makeText(getActivity(), "Bạn chưa nhập từ khóa tìm kiếm", Toast.LENGTH_SHORT).show();
//                }
//                break;
//            case 1:
//                if (key != null && !key.isEmpty()) {
//                    if (arrayList.size() > 0) {
//                        arrayList.clear();
//                    }
//                    if (productAdapter != null) {
//                        productAdapter.notifyDataSetChanged();
//                    }
//                    arrayList = databaseHandler.getProductObject(key);
//                    if (arrayList != null && arrayList.size() > 0) {
//                        productAdapter = new ProductAdapter(getActivity(), arrayList);
//                        listView.setAdapter(productAdapter);
//                    } else {
//                        Toast.makeText(getActivity(), "Không tìm thấy sản phẩm nào", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    Toast.makeText(getActivity(), "Bạn chưa nhập từ khóa tìm kiếm", Toast.LENGTH_SHORT).show();
//                }
//                break;
//            case 2:
//                if (key != null && !key.isEmpty()) {
//                    if (arrayList.size() > 0) {
//                        arrayList.clear();
//                    }
//                    if (productAdapter != null) {
//                        productAdapter.notifyDataSetChanged();
//                    }
//                    arrayList = databaseHandler.getProductMauSac(key);
//                    if (arrayList != null && arrayList.size() > 0) {
//                        productAdapter = new ProductAdapter(getActivity(), arrayList);
//                        listView.setAdapter(productAdapter);
//                    } else {
//                        Toast.makeText(getActivity(), "Không tìm thấy sản phẩm nào", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    Toast.makeText(getActivity(), "Bạn chưa nhập từ khóa tìm kiếm", Toast.LENGTH_SHORT).show();
//                }
//                break;
//            case 3:
//                if (key != null && !key.isEmpty()) {
//                    if (arrayList.size() > 0) {
//                        arrayList.clear();
//                    }
//                    if (productAdapter != null) {
//                        productAdapter.notifyDataSetChanged();
//                    }
//                    arrayList = databaseHandler.getProductSize(key);
//                    if (arrayList != null && arrayList.size() > 0) {
//                        productAdapter = new ProductAdapter(getActivity(), arrayList);
//                        listView.setAdapter(productAdapter);
//                    } else {
//                        Toast.makeText(getActivity(), "Không tìm thấy sản phẩm nào", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    Toast.makeText(getActivity(), "Bạn chưa nhập từ khóa tìm kiếm", Toast.LENGTH_SHORT).show();
//                }
//                break;
//            case 4:
//                if (key != null && !key.isEmpty()) {
//                    if (arrayList.size() > 0) {
//                        arrayList.clear();
//                    }
//                    if (productAdapter != null) {
//                        productAdapter.notifyDataSetChanged();
//                    }
//                    arrayList = databaseHandler.getProductLoai(key);
//                    if (arrayList != null && arrayList.size() > 0) {
//                        productAdapter = new ProductAdapter(getActivity(), arrayList);
//                        listView.setAdapter(productAdapter);
//                    } else {
//                        Toast.makeText(getActivity(), "Không tìm thấy sản phẩm nào", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    Toast.makeText(getActivity(), "Bạn chưa nhập từ khóa tìm kiếm", Toast.LENGTH_SHORT).show();
//                }
//                break;
//            case 5:
//                if (key != null && !key.isEmpty()) {
//                    if (arrayList.size() > 0) {
//                        arrayList.clear();
//                    }
//                    if (productAdapter != null) {
//                        productAdapter.notifyDataSetChanged();
//                    }
//                    arrayList = databaseHandler.getProductCost(key);
//                    if (arrayList != null && arrayList.size() > 0) {
//                        productAdapter = new ProductAdapter(getActivity(), arrayList);
//                        listView.setAdapter(productAdapter);
//                    } else {
//                        Toast.makeText(getActivity(), "Không tìm thấy sản phẩm nào", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    Toast.makeText(getActivity(), "Bạn chưa nhập từ khóa tìm kiếm", Toast.LENGTH_SHORT).show();
//                }
//                break;
//            case 6:
//                if (key != null && !key.isEmpty()) {
//                    if (arrayList.size() > 0) {
//                        arrayList.clear();
//                    }
//                    if (productAdapter != null) {
//                        productAdapter.notifyDataSetChanged();
//                    }
//                    arrayList = databaseHandler.getProductMua(key);
//                    if (arrayList != null && arrayList.size() > 0) {
//                        productAdapter = new ProductAdapter(getActivity(), arrayList);
//                        listView.setAdapter(productAdapter);
//                    } else {
//                        Toast.makeText(getActivity(), "Không tìm thấy sản phẩm nào", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    Toast.makeText(getActivity(), "Bạn chưa nhập từ khóa tìm kiếm", Toast.LENGTH_SHORT).show();
//                }
//                break;
//            case 7:
//                if (key != null && !key.isEmpty()) {
//                    if (arrayList.size() > 0) {
//                        arrayList.clear();
//                    }
//                    if (productAdapter != null) {
//                        productAdapter.notifyDataSetChanged();
//                    }
//                    arrayList = databaseHandler.getProductSale(key);
//                    if (arrayList != null && arrayList.size() > 0) {
//                        productAdapter = new ProductAdapter(getActivity(), arrayList);
//                        listView.setAdapter(productAdapter);
//                    } else {
//                        Toast.makeText(getActivity(), "Không tìm thấy sản phẩm nào", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    Toast.makeText(getActivity(), "Bạn chưa nhập từ khóa tìm kiếm", Toast.LENGTH_SHORT).show();
//                }
//                break;
//            case 8:
//                if (key != null && !key.isEmpty()) {
//                    if (arrayList.size() > 0) {
//                        arrayList.clear();
//                    }
//                    if (productAdapter != null) {
//                        productAdapter.notifyDataSetChanged();
//                    }
//                    arrayList = databaseHandler.getProductNSX(key);
//                    if (arrayList != null && arrayList.size() > 0) {
//                        productAdapter = new ProductAdapter(getActivity(), arrayList);
//                        listView.setAdapter(productAdapter);
//                    } else {
//                        Toast.makeText(getActivity(), "Không tìm thấy sản phẩm nào", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    Toast.makeText(getActivity(), "Bạn chưa nhập từ khóa tìm kiếm", Toast.LENGTH_SHORT).show();
//                }
//                break;
//        }
//    }

    private class GetProductByParams extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ref.setRefreshing(true);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            productAdapter = new ProductAdapter(getActivity(), arrayList);
            listView.setAdapter(productAdapter);

            if (arrayList.size() <= 0) {
                Toast.makeText(getActivity(), "Không tìm thấy sản phẩm nào", Toast.LENGTH_SHORT).show();
            }
            ref.setRefreshing(false);
        }

        @Override
        protected Void doInBackground(String... params) {
            if (params[0] != null && !params[0].isEmpty()) {
                if (pos == 0) {
                    arrayList = databaseHandler.getProductName(params[0]);
                } else if (pos == 1) {
                    arrayList = databaseHandler.getProductObject(params[0]);
                } else if (pos == 2) {
                    arrayList = databaseHandler.getProductMauSac(params[0]);
                } else if (pos == 3) {
                    arrayList = databaseHandler.getProductSize(params[0]);
                } else if (pos == 4) {
                    arrayList = databaseHandler.getProductLoai(params[0]);
                } else if (pos == 5) {
                    arrayList = databaseHandler.getProductCost(params[0]);
                } else if (pos == 6) {
                    arrayList = databaseHandler.getProductMua(params[0]);
                } else if (pos == 7) {
                    arrayList = databaseHandler.getProductSale(params[0]);
                } else if (pos == 8) {
                    arrayList = databaseHandler.getProductNSX(params[0]);
                }
            }
            return null;
        }
    }

    private class GetAllProduct extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ref.setRefreshing(true);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            productAdapter = new ProductAdapter(getActivity(), arrayList);
            listView.setAdapter(productAdapter);

            if (arrayList.size() <= 0) {
                Toast.makeText(getActivity(), "Không tìm thấy sản phẩm nào", Toast.LENGTH_SHORT).show();
            }
            ref.setRefreshing(false);
        }

        @Override
        protected Void doInBackground(Void... params) {
            arrayList = new ArrayList<>();
            arrayList = databaseHandler.getAllProducts();
            return null;
        }
    }
}
