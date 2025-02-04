import pandas as pd
import numpy as np

def find(file, searched_times):

    time_column = 'time'    
    chunk_size = 100_000  
    
    # Convert the searched time to numpy datetime64 objects
    searched_times = np.array(pd.to_datetime(searched_times).values, dtype='datetime64[ns]')
    
    # Initialize results with datetime64 keys
    results = {time: None for time in searched_times}
    
    # Initialize the global index tracker
    current_index = 0
    
    # Process the CSV file in chunks
    for chunk in pd.read_csv(file, usecols=[time_column], chunksize=chunk_size, encoding='utf-8'):
        # Convert the 'time' column to datetime64 format
        chunk[time_column] = pd.to_datetime(chunk[time_column]).values.astype('datetime64[ns]')
        
        # Check each searched times
        for time in searched_times:
            if results[time] is None:  # Only search if the index hasn't been found
                # Find the first index where the time is greater
                idx = np.searchsorted(chunk[time_column], time, side='right')
                if idx < len(chunk):  # Found within the current chunk
                    results[time] = current_index + idx
        
        # Increment the global index by the size of the chunk
        current_index += len(chunk)
    
        # Break early if all results are found
        if all(v is not None for v in results.values()):
            break
    
    result_index = []
    for time, index in results.items():
        #print(f"Timestamp: {time}, index: {index if index is not None else 'Beyond last row'}")
        result_index.append(index)
        
    return result_index