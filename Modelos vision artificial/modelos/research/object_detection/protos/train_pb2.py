# -*- coding: utf-8 -*-
# Generated by the protocol buffer compiler.  DO NOT EDIT!
# source: object_detection/protos/train.proto
"""Generated protocol buffer code."""
from google.protobuf.internal import builder as _builder
from google.protobuf import descriptor as _descriptor
from google.protobuf import descriptor_pool as _descriptor_pool
from google.protobuf import symbol_database as _symbol_database
# @@protoc_insertion_point(imports)

_sym_db = _symbol_database.Default()


from object_detection.protos import optimizer_pb2 as object__detection_dot_protos_dot_optimizer__pb2
from object_detection.protos import preprocessor_pb2 as object__detection_dot_protos_dot_preprocessor__pb2


DESCRIPTOR = _descriptor_pool.Default().AddSerializedFile(b'\n#object_detection/protos/train.proto\x12\x17object_detection.protos\x1a\'object_detection/protos/optimizer.proto\x1a*object_detection/protos/preprocessor.proto\"\xb1\t\n\x0bTrainConfig\x12\x16\n\nbatch_size\x18\x01 \x01(\r:\x02\x33\x32\x12M\n\x19\x64\x61ta_augmentation_options\x18\x02 \x03(\x0b\x32*.object_detection.protos.PreprocessingStep\x12\x1c\n\rsync_replicas\x18\x03 \x01(\x08:\x05\x66\x61lse\x12,\n\x1dkeep_checkpoint_every_n_hours\x18\x04 \x01(\x02:\x05\x31\x30\x30\x30\x30\x12\x35\n\toptimizer\x18\x05 \x01(\x0b\x32\".object_detection.protos.Optimizer\x12$\n\x19gradient_clipping_by_norm\x18\x06 \x01(\x02:\x01\x30\x12\x1e\n\x14\x66ine_tune_checkpoint\x18\x07 \x01(\t:\x00\x12#\n\x19\x66ine_tune_checkpoint_type\x18\x16 \x01(\t:\x00\x12T\n\x1c\x66ine_tune_checkpoint_version\x18\x1c \x01(\x0e\x32*.object_detection.protos.CheckpointVersion:\x02V1\x12,\n\x19\x66rom_detection_checkpoint\x18\x08 \x01(\x08:\x05\x66\x61lseB\x02\x18\x01\x12\x31\n\"load_all_detection_checkpoint_vars\x18\x13 \x01(\x08:\x05\x66\x61lse\x12\x38\n*run_fine_tune_checkpoint_dummy_computation\x18\x1e \x01(\x08:\x04true\x12\x14\n\tnum_steps\x18\t \x01(\r:\x01\x30\x12\x1f\n\x13startup_delay_steps\x18\n \x01(\x02:\x02\x31\x35\x12\x1f\n\x14\x62ias_grad_multiplier\x18\x0b \x01(\x02:\x01\x30\x12\"\n\x1aupdate_trainable_variables\x18\x19 \x03(\t\x12\x18\n\x10\x66reeze_variables\x18\x0c \x03(\t\x12 \n\x15replicas_to_aggregate\x18\r \x01(\x05:\x01\x31\x12%\n\x14\x62\x61tch_queue_capacity\x18\x0e \x01(\x05:\x03\x31\x35\x30\x42\x02\x18\x01\x12&\n\x17num_batch_queue_threads\x18\x0f \x01(\x05:\x01\x38\x42\x02\x18\x01\x12&\n\x17prefetch_queue_capacity\x18\x10 \x01(\x05:\x01\x35\x42\x02\x18\x01\x12)\n\x1amerge_multiple_label_boxes\x18\x11 \x01(\x08:\x05\x66\x61lse\x12$\n\x15use_multiclass_scores\x18\x18 \x01(\x08:\x05\x66\x61lse\x12%\n\x17\x61\x64\x64_regularization_loss\x18\x12 \x01(\x08:\x04true\x12$\n\x13max_number_of_boxes\x18\x14 \x01(\x05:\x03\x31\x30\x30\x42\x02\x18\x01\x12\'\n\x19unpad_groundtruth_tensors\x18\x15 \x01(\x08:\x04true\x12%\n\x16retain_original_images\x18\x17 \x01(\x08:\x05\x66\x61lse\x12\x1b\n\x0cuse_bfloat16\x18\x1a \x01(\x08:\x05\x66\x61lse\x12\"\n\x13summarize_gradients\x18\x1b \x01(\x08:\x05\x66\x61lse*0\n\x11\x43heckpointVersion\x12\x0b\n\x07UNKNOWN\x10\x00\x12\x06\n\x02V1\x10\x01\x12\x06\n\x02V2\x10\x02')

_builder.BuildMessageAndEnumDescriptors(DESCRIPTOR, globals())
_builder.BuildTopDescriptorsAndMessages(DESCRIPTOR, 'object_detection.protos.train_pb2', globals())
if _descriptor._USE_C_DESCRIPTORS == False:

  DESCRIPTOR._options = None
  _TRAINCONFIG.fields_by_name['from_detection_checkpoint']._options = None
  _TRAINCONFIG.fields_by_name['from_detection_checkpoint']._serialized_options = b'\030\001'
  _TRAINCONFIG.fields_by_name['batch_queue_capacity']._options = None
  _TRAINCONFIG.fields_by_name['batch_queue_capacity']._serialized_options = b'\030\001'
  _TRAINCONFIG.fields_by_name['num_batch_queue_threads']._options = None
  _TRAINCONFIG.fields_by_name['num_batch_queue_threads']._serialized_options = b'\030\001'
  _TRAINCONFIG.fields_by_name['prefetch_queue_capacity']._options = None
  _TRAINCONFIG.fields_by_name['prefetch_queue_capacity']._serialized_options = b'\030\001'
  _TRAINCONFIG.fields_by_name['max_number_of_boxes']._options = None
  _TRAINCONFIG.fields_by_name['max_number_of_boxes']._serialized_options = b'\030\001'
  _CHECKPOINTVERSION._serialized_start=1353
  _CHECKPOINTVERSION._serialized_end=1401
  _TRAINCONFIG._serialized_start=150
  _TRAINCONFIG._serialized_end=1351
# @@protoc_insertion_point(module_scope)
