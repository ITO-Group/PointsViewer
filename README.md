# GestureRecognition
THU-HUAWEI cooperation research project, a simple android app that display capacity values of huawei p20 smartphone
### Note
- `screen_value[i][j] = raw_diff_data[i*col_num+j], where col_num=16`
- We selected 47 points as a set of sample for observation, which the first 32 points are along the 0th column, and the other 15 poitns are along the bottom (31th) row.
  - `for 0<=i<32: point_value[i] = raw_diff_data[i*col_num]` 
  - `for i>=32: point_value[i] = raw_diff_data[(row_num-1)*col_num+i-row_num+1], where row_num=32 and col_num=16`

